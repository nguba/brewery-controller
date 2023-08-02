package application.usecase;

import domain.Vessel;

/**
 * System configurations are tasks that happen occasionally, such as adding a new vessel to the system.
 */
public interface SystemConfigurationUseCase {

    /**
     * Register a new vessel in the system.  Temperature controllers are mapped to the vessel.  This is necessary
     * to enable other modules to locate the correct temperature controller for a given vessel on the modbus network.
     *
     * @param vessel
     */
    void registerVessel(Vessel vessel);
}
