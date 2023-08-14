package application.port.output;

import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfigurationPersistenceLayerOutputPortAdapterInMemory implements ConfigurationPersistenceLayerOutputPort {

    private final Map<VesselId, TemperatureControllerId> controllers = new HashMap<>();

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {
        controllers.put(id, temperatureControllerId);
    }

    @Override
    public void removeTemperatureController(VesselId id) {
        controllers.remove(id);
    }

    @Override
    public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId id) {
        return Optional.ofNullable(controllers.get(id));
    }
}
