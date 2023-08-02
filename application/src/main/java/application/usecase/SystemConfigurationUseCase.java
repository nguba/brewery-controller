package application.usecase;

import domain.Vessel;

/**
 * System configurations are tasks that happen occasionally, such as adding a new vessel to the system.
 */
public interface SystemConfigurationUseCase {

    void addVessel(Vessel vessel);
}
