package application.port.output;

import domain.Schedule;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TemperatureControllerOutputPortAdapterInMemory implements TemperatureControllerOutputPort {

    private final Map<VesselId, TemperatureControllerId> controllers = new HashMap<>();

    @Override
    public void saveProfile(VesselId id, Schedule profile) {

    }

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {
        controllers.put(id, temperatureControllerId);
    }

    @Override
    public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId id) {
        return Optional.ofNullable(controllers.get(id));
    }
}
