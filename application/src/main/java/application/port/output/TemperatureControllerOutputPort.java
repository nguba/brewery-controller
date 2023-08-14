package application.port.output;

import domain.TemperatureControllerId;

public interface TemperatureControllerOutputPort {
    /**
     * Request metrics from the temperature controller.  We do this in a bulk query to reduce protocol overhead.
     * Because of the asynchronous nature of the protocol, we will receive the metrics in a separate event.
     *
     * @param temperatureControllerId
     */
    void requestMetrics(TemperatureControllerId temperatureControllerId, EventPublisherOutputPort eventPublisherOutputPort);
}
