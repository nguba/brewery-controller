package application.port.input;

import application.port.output.ConfigurationPersistenceLayerOutputPort;
import application.usecase.SystemConfigurationUseCase;
import domain.Vessel;
import domain.VesselId;

import java.util.Optional;

public class SystemConfigurationInputPort implements SystemConfigurationUseCase {

    private final ConfigurationPersistenceLayerOutputPort outputPort;

    private SystemConfigurationInputPort(ConfigurationPersistenceLayerOutputPort outputPort) {

        this.outputPort = outputPort;
    }

    public static SystemConfigurationInputPort with(ConfigurationPersistenceLayerOutputPort outputPort) {
        return new SystemConfigurationInputPort(outputPort);
    }

    @Override
    public void registerVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        // we should explore other ways to validate the vessel
        if (vessel.temperatureControllerId() == null) {
            throw new IllegalArgumentException("Vessel cannot have a null temperature controller");
        }
        outputPort.findTemperatureControllerId(vessel.id()).ifPresentOrElse(
                existingVessel -> {
                    throw new IllegalArgumentException("Vessel with " + vessel.id() + " already exists, remove the vessel first");
                },
                () -> {
                    outputPort.registerTemperatureController(vessel.id(), vessel.temperatureControllerId());
                }
        );
    }

    @Override
    public void removeVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        outputPort.findTemperatureControllerId(vessel.id()).ifPresentOrElse(
                existingVessel -> {
                    outputPort.removeTemperatureController(vessel.id());
                },
                () -> {
                    throw new IllegalArgumentException("Vessel with " + vessel.id() + " does not exist");
                }
        );
    }

    @Override
    public Optional<Vessel> locateVessel(VesselId id) {
        return Optional.empty();
    }
}
