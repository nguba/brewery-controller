package application.port.output;

import domain.Schedule;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public interface TemperatureControllerOutputPort {

    void saveProfile(VesselId id, Schedule profile);


    void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId);

    Optional<TemperatureControllerId> findTemperatureControllerId(VesselId id);
}
