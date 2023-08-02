package application.usecase;

import domain.TemperatureProfile;
import domain.VesselId;

public interface TemperatureProfileUseCase {

    public void saveProfile(VesselId id, TemperatureProfile profile);
}
