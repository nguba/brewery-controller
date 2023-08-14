package application.port.output;

import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Optional;

public interface ConfigurationPersistenceLayerOutputPort {
    /**
     * Lookup the temperature controller id for a given vessel id.
     *
     * @param vesselId
     * @return the temperature controller id or empty if not found
     */
    Optional<TemperatureControllerId> findTemperatureControllerId(VesselId vesselId);

    void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId);

    void removeTemperatureController(VesselId id);
}
