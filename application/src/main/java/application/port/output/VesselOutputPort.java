package application.port.output;

import domain.TemperatureControllerId;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

import java.util.Optional;

public interface VesselOutputPort {

    Optional<Vessel> findVessel(VesselId id);

    void saveProfile(VesselId id, TemperatureProfile profile);

    void addVessel(Vessel vessel);

    void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId);

    TemperatureControllerId findTemperatureControllerId(VesselId id);
}
