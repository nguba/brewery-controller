package application.port.output;

import domain.Schedule;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public interface DataLoggerOutputPort {

    void saveProfile(VesselId id, Schedule profile);


    void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId);

    void removeTemperatureController(VesselId id);

    Optional<TemperatureControllerId> findTemperatureControllerId(VesselId id);
}
