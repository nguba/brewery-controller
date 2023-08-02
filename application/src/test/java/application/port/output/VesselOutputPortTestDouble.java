package application.port.output;

import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VesselOutputPortTestDouble implements VesselOutputPort {

    private final Map<VesselId, Vessel> vessels = new HashMap<>();

    @Override
    public Optional<Vessel> findVessel(VesselId id) {
        return Optional.ofNullable(vessels.get(id));
    }

    @Override
    public void saveProfile(VesselId id, TemperatureProfile profile) {
        findVessel(id).ifPresent(vessel -> vessel.profile(profile));
    }

    @Override
    public void addVessel(Vessel vessel) {
        vessels.put(vessel.id(), vessel);
    }
}
