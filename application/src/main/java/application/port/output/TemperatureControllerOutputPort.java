package application.port.output;

import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public interface TemperatureControllerOutputPort {
    /**
     * Lookup the temperature controller id for a given vessel id.
     *
     * @param vesselId
     * @return the temperature controller id or empty if not found
     */
    Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId);

    /**
     * Request metrics from the temperature controller.  We do this in a bulk query to reduce protocol overhead.
     * Because of the asynchronous nature of the protocol, we will receive the metrics in a separate event.
     *
     * @param temperatureControllerId
     */
    void requestMetrics(TemperatureControllerId temperatureControllerId, EventPublisherOutputPort eventPublisherOutputPort);
}
