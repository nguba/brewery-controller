package brewery.outputs.pxu;

import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// TODO consider moving this into a Junit5 extension
public abstract class PxuFixture<T> implements PxuReadListener<T> {

    protected static final int UNIT_ID = 6;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuFixture.class);
    protected static PxuNetwork pxu;

    private final CountDownLatch latch = new CountDownLatch(1);

    protected T results;

    protected abstract Runnable networkCommand(PxuReadListener<T> listener);

    @BeforeAll
    static void init() throws Exception {
        final SerialParameters parameters = new SerialParameters();
        parameters.setPortName("COM3");
        parameters.setEncoding("rtu");
        parameters.setBaudRate(9600);
        parameters.setParity("None");
        parameters.setDatabits(8);

        pxu = new PxuNetwork(parameters, Duration.ofMillis(100)).start();
    }



    @BeforeEach
    void setUp() throws InterruptedException {
        networkCommand(this).run();
        awaitResult();
    }


    @AfterAll
    static void tearDown()  {
        pxu.stop();
    }

    @Override
    public void onRead(T value) {
        results = value;
        latch.countDown();
        LOGGER.info("onRead: {}", value);
    }

    protected void awaitResult() throws InterruptedException {
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }
}
