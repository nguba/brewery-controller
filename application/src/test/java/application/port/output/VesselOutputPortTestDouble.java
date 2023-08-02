package application.port.output;

import domain.TemperatureControllerId;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VesselOutputPortTestDouble implements VesselOutputPort {

    private final Map<VesselId, Vessel> vessels = new HashMap<>();

    private final Map<VesselId, TemperatureControllerId> controllers = new HashMap<>();

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

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {
        controllers.put(id, temperatureControllerId);
    }

    @Override
    public TemperatureControllerId findTemperatureControllerId(VesselId id) {
        return controllers.get(id);
    }
}
