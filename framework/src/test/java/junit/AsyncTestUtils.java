package junit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AsyncTestUtils {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void awaitResult(int seconds) throws InterruptedException {
        // timeout when device doesn't respond
        if (!latch.await(seconds, TimeUnit.SECONDS)) {
            throw new RuntimeException("Device did not respond in time");
        }
    }

    public void executeAndWait(Runnable runnable, int timeout) throws InterruptedException {
        runnable.run();
        awaitResult(timeout);
    }

    public void notifySuccess() {
        latch.countDown();
    }
}
