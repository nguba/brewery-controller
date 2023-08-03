package framework.adapter.output.pxu;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import framework.adapter.output.pxu.event.MetricsFetched;
import junit.AsyncTestUtils;
import junit.PxuTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@PxuTest
class PxuMetricsTest {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuMetricsTest.class);

    private final PxuNetwork pxu;

    private PxuMetrics results;

    private final AsyncTestUtils asyncTestUtils = new AsyncTestUtils();

    PxuMetricsTest(PxuNetwork pxu) {
        this.pxu = pxu;
    }

    @Autowired
    private EventBus eventBus;

    @BeforeEach
    void setUp() throws InterruptedException {
        eventBus.register(this);
        asyncTestUtils.executeAndWait(() -> pxu.queryMetrics(PxuTestConstants.UNIT_ID), 30);
    }

    @Subscribe
    public void metricsAvailable(MetricsFetched event) {
        LOGGER.info("{}", event);
        results = event.value();
        asyncTestUtils.notifySuccess();
    }

    @Test
    void readProcessValue() {
        double value = results.processValue();
        LOGGER.info("pV={}", value);
    }

    @Test
    void readActiveSetpoint() {
        double value = results.activeSetpoint();
        LOGGER.info("sP={}", value);
    }

    @Test
    void readStatus() {
        String value = results.status();
        assertThat(value).isNotNull();
        LOGGER.info("status={}", value);
    }

    @Test
    void readOutputOne() {
        double value = results.outputOne();
        LOGGER.info("OUT1={}", value);
    }

    @Test
    void readOutputTwo() {
        double actual = results.outputTwo();
        LOGGER.info("OUT2={}", actual);
    }

    @Test
    void readPidParameterSet() {
        String value = results.pidParameterSet();
        assertThat(value).isNotNull();
        LOGGER.info("pidSet={}", value);
    }

    @Test
    void readCurrentProfile() {
        int value = results.currentProfile();
        LOGGER.info("currentProfile={}", value);
    }

    @Test
    void readCurrentProfileSegment() {
        int value = results.currentProfileSegment();
        LOGGER.info("currentProfileSeg={}", value);
    }

    @Test
    void profileSegmentRemainingTime() {
        Duration value = results.profileSegmentRemainingTime();
        assertThat(value).isNotNull();
        LOGGER.info("profileSegRemTime={}s", value.getSeconds());
    }
}