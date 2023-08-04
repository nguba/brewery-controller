package framework.adapter.output.pxu;

import application.port.output.EventPublisherOutputPort;
import application.port.output.TemperatureControllerOutputPort;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public class TemperatureControllerOutputPortPxuAdapter implements TemperatureControllerOutputPort {
    @Override
    public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId) {
        return Optional.empty();
    }

    @Override
    public void requestMetrics(TemperatureControllerId temperatureControllerId, EventPublisherOutputPort eventPublisherOutputPort) {

    }
}
