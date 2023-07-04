package junit;

import adapter.outputs.pxu.PxuReadListener;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

// TODO consider moving this into a Junit5 extension
public abstract class PxuFixture<T> implements PxuReadListener<T> {

    protected static final int UNIT_ID = 6;

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuFixture.class);

    private final CountDownLatch latch = new CountDownLatch(1);

    protected T results;

    protected abstract Runnable networkCommand(PxuReadListener<T> listener);


    @BeforeEach
    void setUp() throws InterruptedException {
        networkCommand(this).run();
        awaitResult();
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
