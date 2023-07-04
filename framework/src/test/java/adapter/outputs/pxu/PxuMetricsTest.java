package adapter.outputs.pxu;

import junit.PxuFixture;
import junit.extension.PxuNetworkExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PxuNetworkExtension.class)
class PxuMetricsTest extends PxuFixture<PxuMetrics> {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuMetricsTest.class);
    private final PxuNetwork pxu;

    PxuMetricsTest(PxuNetwork pxu) {
        this.pxu = pxu;
    }

    @Override
    protected Runnable networkCommand(PxuReadListener<PxuMetrics> listener) {
        return () -> pxu.queryMetrics(UNIT_ID, listener);
    }

    @Test
    void hasUnitId() {
        assertThat(results.unitId()).isEqualTo(UNIT_ID);
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