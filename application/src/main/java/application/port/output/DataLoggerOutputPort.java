package application.port.output;

import domain.Schedule;
import domain.VesselId;

public interface DataLoggerOutputPort {

    void saveProfile(VesselId id, Schedule profile);
}
