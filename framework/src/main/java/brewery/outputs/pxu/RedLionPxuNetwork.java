package brewery.outputs.pxu;

import brewery.outputs.ModbusRequest;
import brewery.outputs.RedLionProfileReader;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.*;

public class RedLionPxuNetwork {
    private final ModbusSerialMaster master;

    private final PxuManager pxuManager;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RedLionPxuNetwork.class);

    public RedLionPxuNetwork(SerialParameters parameters, Duration pollRate) {
        master = new ModbusSerialMaster(parameters);
        pxuManager = new PxuManager(pollRate.toMillis());
    }

    public RedLionPxuNetwork start() throws Exception {
        master.connect();
        pxuManager.start();
        LOGGER.info("Started {}", this.getClass().getSimpleName());
        return this;
    }

    public void queryMetrics(int unitId, RedLionNetworkListener<RedLionMetrics> listener)  {
        pxuManager.queue(new QueryMetricsRequest(unitId, master, listener));
    }

    public RedLionProfileReader makeProfileReader(int unitId) throws ModbusException {
        Register[] regs = master.readMultipleRegisters(unitId, 1100, 30);
        return new RedLionProfileReader(unitId, regs);
    }

    public void stop() throws Exception {
        pxuManager.stop();
        master.disconnect();
        LOGGER.info("Stopped {}", this.getClass().getSimpleName());
    }

    /**
     * We need to poll the temperature controllers (about 5), who are on the same modbus interface to either configure them,
     * or obtain readings.  These cannot occur in parallel since modbus is a serial interface.  This class
     * ensures that tasks are executed serially and after each has completed.
     */
    private static class PxuManager implements Runnable {
        private final BlockingQueue<ModbusRequest> queue = new LinkedBlockingQueue<>();
        private final ScheduledExecutorService consumer = Executors.newSingleThreadScheduledExecutor();
        private Optional<ScheduledFuture<?>> scheduledFuture = Optional.empty();

        private final long delay;

        private PxuManager(long delay) {
            this.delay = delay;
        }

        public void start() {
            this.scheduledFuture =
                    Optional.of(consumer.scheduleWithFixedDelay(this, 0, delay, TimeUnit.MILLISECONDS));
        }

        @Override
        public void run() {
            try {
                final ModbusRequest request = queue.take();
                request.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void stop() {
            scheduledFuture.ifPresent((scheduler) -> scheduler.cancel(true));
        }

        public void queue(ModbusRequest request) {
            queue.add(request);
        }
    }

    public static class QueryMetricsRequest implements ModbusRequest {

        private final int unitId;
        private final AbstractModbusMaster master;
        private final RedLionNetworkListener<RedLionMetrics> listener;

        public QueryMetricsRequest(int unitId, AbstractModbusMaster master, RedLionNetworkListener<RedLionMetrics> listener) {
            this.unitId = unitId;
            this.master = master;
            this.listener = listener;
        }

        @Override
        public void execute() {
            try {
                Register[] regs = master.readMultipleRegisters(unitId, 0, 30);
                RedLionMetrics metrics = new RedLionMetrics(unitId, regs);
                listener.onRead(metrics);
            } catch (ModbusException e) {
               System.err.println(e.getMessage());
            }
        }
    }
}
