package application.port.input;

import application.port.output.TemperatureControllerOutputPort;
import application.usecase.VesselMonitorUseCase;
import domain.Interval;
import domain.TemperatureControllerId;
import domain.VesselId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

class VesselMonitorInputPortTest {
    Interval interval = Interval.of(Duration.ofSeconds(1));

    VesselId vesselId = VesselId.ofMashTun();

    CountDownLatch latch = new CountDownLatch(1);

    VesselMonitorUseCase vesselMonitor = VesselMonitorInputPort.with(new TemperatureControllerOutputPort() {
        @Override
        public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId) {
            return Optional.of(TemperatureControllerId.of(6));
        }

        @Override
        public void requestMetrics(TemperatureControllerId temperatureControllerId) {
            latch.countDown();
        }
    }, Executors.newScheduledThreadPool(1));

    @Test
    @DisplayName("Verify that monitoring a vessel with a null vessel id throws an exception")
    void startMonitoring_NullVesselId() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> vesselMonitor.startMonitoring(null, interval))
                .withMessage("Vessel id cannot be null");
    }

    @Test
    @DisplayName("Verify that monitoring a vessel with a null interval throws an exception")
    void startMonitoring_NullInterval() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> vesselMonitor.startMonitoring(vesselId, null))
                .withMessage("Interval cannot be null");
    }

    @Test
    @DisplayName("Starting monitoring of a vessel creates an entry in the monitored vessels map")
    void startMonitoringIsActive() {
        assertThat(vesselMonitor.isActive(vesselId)).isFalse();

        vesselMonitor.startMonitoring(vesselId, interval);

        assertThat(vesselMonitor.isActive(vesselId)).isTrue();
    }

    @Test
    @DisplayName("Starting monitoring of a vessel sends a request for metrics to the temperature controller")
    void startMonitoringRequestMetrics() throws InterruptedException {
        vesselMonitor.startMonitoring(vesselId, interval);

        assertThat(latch.await(5, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    @DisplayName("Starting monitoring of a vessel throws an exception if the vessel is already being monitored")
    void startMonitoringAlreadyMonitored() {
        vesselMonitor.startMonitoring(vesselId, interval);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> vesselMonitor.startMonitoring(vesselId, interval))
                .withMessage("Vessel is already being monitored");
    }

    @Test
    @DisplayName("Starting monitoring of a vessel throws an exception if the temperature controller Id cannot be found")
    void startMonitoringTemperatureControllerIdNotFound() {
        vesselMonitor = VesselMonitorInputPort.with(new TemperatureControllerOutputPort() {
            @Override
            public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId) {
                return Optional.empty();
            }

            @Override
            public void requestMetrics(TemperatureControllerId temperatureControllerId) {
            }
        }, Executors.newScheduledThreadPool(1));

        assertThatIllegalStateException()
                .isThrownBy(() -> vesselMonitor.startMonitoring(vesselId, interval))
                .withMessage("No temperature controller found for VesselId[value=MashTun]");
    }

    @Test
    @DisplayName("Stopping monitoring of a vessel succeeds silently if the vessel is not being monitored")
    void stopMonitoringNotMonitored() {
        vesselMonitor.stopMonitoring(vesselId);

        assertThat(vesselMonitor.isActive(vesselId)).isFalse();
    }

    @Test
    @DisplayName("Stopping monitoring of a vessel succeeds if the vessel is being monitored")
    void stopMonitoring() {
        vesselMonitor.startMonitoring(vesselId, interval);

        assertThat(vesselMonitor.isActive(vesselId)).isTrue();

        vesselMonitor.stopMonitoring(vesselId);

        assertThat(vesselMonitor.isActive(vesselId)).isFalse();
    }

    @Test
    @DisplayName("Stopping monitoring throws an exception if the vessel id is null")
    void stopMonitoring_NullVesselId() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> vesselMonitor.stopMonitoring(null))
                .withMessage("Vessel id cannot be null");
    }
}