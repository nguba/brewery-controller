package application.port.input;

import application.port.output.TemperatureControllerOutputPort;
import application.usecase.SystemConfigurationUseCase;
import domain.Vessel;

public class SystemConfigurationInputPort implements SystemConfigurationUseCase {

    private final TemperatureControllerOutputPort temperatureControllerOutputPort;

    private SystemConfigurationInputPort(TemperatureControllerOutputPort temperatureControllerOutputPort) {
        this.temperatureControllerOutputPort = temperatureControllerOutputPort;
    }

    public static SystemConfigurationInputPort with(TemperatureControllerOutputPort temperatureControllerOutputPort) {
        return new SystemConfigurationInputPort(temperatureControllerOutputPort);
    }

    @Override
    public void registerVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        // we should explore other ways to validate the vessel
        if (vessel.temperatureControllerId() == null) {
            throw new IllegalArgumentException("Vessel cannot have a null temperature controller");
        }
        temperatureControllerOutputPort.findTemperatureControllerId(vessel.id()).ifPresentOrElse(
                existingVessel -> {
                    throw new IllegalArgumentException("Vessel with " + vessel.id() + " already exists, remove the vessel first");
                },
                () -> {
                    temperatureControllerOutputPort.registerTemperatureController(vessel.id(), vessel.temperatureControllerId());
                }
        );
    }
}
