package brewery.outputs.pxu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class RedLionMetricsTest extends PxuFixture implements RedLionNetworkListener<RedLionMetrics> {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RedLionMetricsTest.class);

    RedLionMetrics redLionMetrics;

    CountDownLatch latch = new CountDownLatch(1);

    @BeforeEach
    void setUp() throws Exception {
        pxu.queryMetrics(6, this);
        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void readProcessValue() {
        double value = redLionMetrics.processValue();
        LOGGER.info("pV={}", value);
    }

    @Test
    void readActiveSetpoint() {
        double value = redLionMetrics.activeSetpoint();
        LOGGER.info("sP={}", value);
    }

    @Test
    void readStatus() {
        String value = redLionMetrics.status();
        assertThat(value).isNotNull();
        LOGGER.info("status={}", value);
    }

    @Test
    void readOutputOne() {
        double value = redLionMetrics.outputOne();
        LOGGER.info("OUT1={}", value);
    }

    @Test
    void readOutputTwo() {
        double actual = redLionMetrics.outputTwo();
        LOGGER.info("OUT2={}", actual);
    }

    @Test
    void readPidParameterSet() {
        String value = redLionMetrics.pidParameterSet();
        assertThat(value).isNotNull();
        LOGGER.info("pidSet={}", value);
    }

    @Test
    void readCurrentProfile()  {
        int value = redLionMetrics.currentProfile();
        LOGGER.info("currentProfile={}", value);
    }

    @Test
    void readCurrentProfileSegment() {
        int value = redLionMetrics.currentProfileSegment();
        LOGGER.info("currentProfileSeg={}", value);
    }

    @Test
    void profileSegmentRemainingTime()  {
        Duration value = redLionMetrics.profileSegmentRemainingTime();
        assertThat(value).isNotNull();
        LOGGER.info("profileSegRemTime={}s", value.getSeconds());
    }

    @Override
    public void onRead(RedLionMetrics metrics) {
        latch.countDown();
        this.redLionMetrics = metrics;
    }
}