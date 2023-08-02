package application.port.input;

import application.port.output.VesselOutputPort;
import application.usecase.TemperatureProfileUseCase;
import domain.TemperatureProfile;
import domain.VesselId;

public class TemperatureProfileUseCaseInputPort implements TemperatureProfileUseCase {

    private TemperatureProfileUseCaseInputPort(VesselOutputPort vesselOutputPort) {
        this.vesselOutputPort = vesselOutputPort;
    }

    public static TemperatureProfileUseCase with(VesselOutputPort vesselOutputPort) {
        return new TemperatureProfileUseCaseInputPort(vesselOutputPort);
    }

    private final VesselOutputPort vesselOutputPort;

    @Override
    public void saveProfile(VesselId id, TemperatureProfile profile) {
        if (id == null)
            throw new IllegalArgumentException("Saving a temperature profile requires a vessel id");
        if (profile == null)
            throw new IllegalArgumentException("Temperature profile cannot be null");
        vesselOutputPort.saveProfile(id, profile);
    }
}
