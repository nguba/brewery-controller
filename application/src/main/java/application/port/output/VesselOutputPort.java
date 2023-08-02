package application.port.output;

import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

import java.util.Optional;

public interface VesselOutputPort {

    Optional<Vessel> findVessel(VesselId id);

    void saveProfile(VesselId id, TemperatureProfile profile);

    void addVessel(Vessel vessel);
}
