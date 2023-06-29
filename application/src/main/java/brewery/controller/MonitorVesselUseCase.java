package brewery.controller;

import brewery.controller.ports.PidControllerPort;

public class MonitorVesselUseCase {


    private final PidControllerPort pidControllerPort;

    public MonitorVesselUseCase(PidControllerPort pidControllerPort) {

        this.pidControllerPort = pidControllerPort;
    }
}
