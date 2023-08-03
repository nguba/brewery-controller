package application.port.input;

import application.port.output.DataLoggerOutputPort;
import application.usecase.SystemConfigurationUseCase;
import domain.Vessel;

public class SystemConfigurationInputPort implements SystemConfigurationUseCase {

    private final DataLoggerOutputPort dataLoggerOutputPort;

    private SystemConfigurationInputPort(DataLoggerOutputPort dataLoggerOutputPort) {
        this.dataLoggerOutputPort = dataLoggerOutputPort;
    }

    public static SystemConfigurationInputPort with(DataLoggerOutputPort dataLoggerOutputPort) {
        return new SystemConfigurationInputPort(dataLoggerOutputPort);
    }

    @Override
    public void registerVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        // we should explore other ways to validate the vessel
        if (vessel.temperatureControllerId() == null) {
            throw new IllegalArgumentException("Vessel cannot have a null temperature controller");
        }
        dataLoggerOutputPort.findTemperatureControllerId(vessel.id()).ifPresentOrElse(
                existingVessel -> {
                    throw new IllegalArgumentException("Vessel with " + vessel.id() + " already exists, remove the vessel first");
                },
                () -> {
                    dataLoggerOutputPort.registerTemperatureController(vessel.id(), vessel.temperatureControllerId());
                }
        );
    }

    @Override
    public void removeVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        dataLoggerOutputPort.findTemperatureControllerId(vessel.id()).ifPresentOrElse(
                existingVessel -> {
                    dataLoggerOutputPort.removeTemperatureController(vessel.id());
                },
                () -> {
                    throw new IllegalArgumentException("Vessel with " + vessel.id() + " does not exist");
                }
        );
    }
}
