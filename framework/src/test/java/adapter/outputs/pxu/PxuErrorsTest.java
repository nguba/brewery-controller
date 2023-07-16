package adapter.outputs.pxu;

import adapter.outputs.pxu.event.PxuRequestFailed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import junit.AsyncTestUtils;
import junit.PxuTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@PxuTest
public class PxuErrorsTest {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuErrorsTest.class);

    private final PxuNetwork pxu;

    public PxuErrorsTest(PxuNetwork pxu) {
        this.pxu = pxu;
    }

    private final AsyncTestUtils asyncTestUtils = new AsyncTestUtils();

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EventBus eventBus;

    @BeforeEach
    void setUp() {
        eventBus.register(this);
    }

    @Subscribe
    void onPxuRequestFailed(PxuRequestFailed event) {
        LOGGER.info("Received {}", event);
        asyncTestUtils.notifySuccess();
    }

    @Test
    void queryUnavailableDeviceId() throws InterruptedException {
        asyncTestUtils.executeAndWait(() -> pxu.queryMetrics(25), 30);
    }
}
