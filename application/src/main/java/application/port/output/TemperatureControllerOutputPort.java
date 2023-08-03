package application.port.output;

import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public interface TemperatureControllerOutputPort {
    Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId);

    void requestMetrics(TemperatureControllerId temperatureControllerId);
}
