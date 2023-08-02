package application.port.input;

import application.port.output.VesselOutputPort;
import application.usecase.SystemConfigurationUseCase;
import domain.Vessel;

public class SystemConfigurationInputPort implements SystemConfigurationUseCase {

    private final VesselOutputPort vesselOutputPort;

    private SystemConfigurationInputPort(VesselOutputPort vesselOutputPort) {
        this.vesselOutputPort = vesselOutputPort;
    }

    public static SystemConfigurationInputPort with(VesselOutputPort vesselOutputPort) {
        return new SystemConfigurationInputPort(vesselOutputPort);
    }

    @Override
    public void addVessel(Vessel vessel) {
        if (vessel == null)
            throw new IllegalArgumentException("Vessel cannot be null");
        // we should explore other ways to validate the vessel
        if (vessel.temperatureControllerId() == null) {
            throw new IllegalArgumentException("Vessel cannot have a null temperature controller");
        }
        vesselOutputPort.addVessel(vessel);
        vesselOutputPort.registerTemperatureController(vessel.id(), vessel.temperatureControllerId());
    }
}
