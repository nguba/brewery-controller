package framework.adapter.output.pxu;

import application.port.output.EventPublisherOutputPort;
import application.port.output.TemperatureControllerOutputPort;
import domain.TemperatureControllerId;

public class TemperatureControllerOutputPortPxuAdapter implements TemperatureControllerOutputPort {
    @Override
    public void requestMetrics(TemperatureControllerId temperatureControllerId, EventPublisherOutputPort eventPublisherOutputPort) {

    }
}
