package adapter.outputs.pxu;

import adapter.outputs.pxu.event.MetricsFetched;
import adapter.outputs.pxu.event.ProfileFetched;
import adapter.outputs.pxu.event.PxuEvent;
import adapter.outputs.pxu.event.PxuRequestFailed;
import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.*;

public class PxuNetwork {
    private final ModbusSerialMaster master;

    private final RequestQueue requestQueue;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuNetwork.class);

    public PxuNetwork(ModbusSerialMaster master, Duration pollRate, EventPublisher eventPublisher) {
        this.master = master;
        LOGGER.info("Detected commPorts: {}", master.getConnection().getCommPorts());
        requestQueue = new RequestQueue(pollRate.toMillis(), eventPublisher);
    }

    public void start() throws Exception {
        master.connect();
        requestQueue.start();
        LOGGER.info("Started {} on {} {}", this.getClass().getSimpleName(), master.getConnection().getDescriptivePortName(), master.getConnection().getPortName());
    }

    public void queryMetrics(int unitId) {
        requestQueue.queue(new QueryMetricsRequest(unitId, master));
    }

    public void queryProfile(int unitId) {
        requestQueue.queue(new QueryProfileRequest(unitId, master));
    }

    public void stop() {
        requestQueue.stop();
        master.disconnect();
        LOGGER.info("Stopped {}", this.getClass().getSimpleName());
    }

    /**
     * We need to poll the temperature controllers (about 5), who are on the same modbus interface to either configure them,
     * or obtain readings.  These cannot occur in parallel since modbus is a serial interface.  This class
     * ensures that tasks are executed serially and after each has completed.
     */
    private static class RequestQueue implements Runnable {
        private final BlockingQueue<ModbusRequest> queue = new LinkedBlockingQueue<>();
        private final ScheduledExecutorService consumer = Executors.newSingleThreadScheduledExecutor();
        private Optional<ScheduledFuture<?>> scheduledFuture = Optional.empty();
        private final long delay;
        private final EventPublisher eventPublisher;
        private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RequestQueue.class);

        private RequestQueue(long delay, EventPublisher eventPublisher) {
            this.delay = delay;
            this.eventPublisher = eventPublisher;
        }

        public void start() {
            this.scheduledFuture =
                    Optional.of(consumer.scheduleWithFixedDelay(this, 0, delay, TimeUnit.MILLISECONDS));
        }

        @Override
        public void run() {
            try {
                final ModbusRequest request = queue.take();
                try {
                    PxuEvent<?> event = request.execute();
                    eventPublisher.publish(event);
                } catch (Exception e) {
                    LOGGER.error("Error executing {}: {}", request.getClass().getSimpleName(), e.getMessage());
                    eventPublisher.publish(PxuRequestFailed.of(request.unitId(), e));
                }
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

    private record QueryMetricsRequest(int unitId, AbstractModbusMaster master) implements ModbusRequest {
        public MetricsFetched execute() throws Exception {
            final Register[] regs = master.readMultipleRegisters(unitId, 0, 30);
            return MetricsFetched.of(unitId, new PxuMetrics(regs));
        }
    }

    public interface ModbusRequest {
        PxuEvent<?> execute() throws Exception;

        int unitId();
    }

    private record QueryProfileRequest(int unitId, ModbusSerialMaster master) implements ModbusRequest {
        @Override
        public ProfileFetched execute() throws Exception {
            final Register[] regs = master.readMultipleRegisters(unitId, 1100, 30);
            return ProfileFetched.of(unitId, new PxuProfile(regs));
        }
    }
}
