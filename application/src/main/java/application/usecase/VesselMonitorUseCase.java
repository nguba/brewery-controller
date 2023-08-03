package application.usecase;

import domain.Interval;
import domain.VesselId;

/**
 * Use case for monitoring the temperature of a vessel.
 */
public interface VesselMonitorUseCase {

    /**
     * Starts monitoring the temperature of a vessel at the given interval.
     * If the vessel is already being monitored, then an exception is thrown.
     *
     * @param vesselId
     * @param interval
     */
    void startMonitoring(VesselId vesselId, Interval interval);

    /**
     * Stops monitoring the temperature of a vessel.
     * If the vessel is not being monitored, then the call succeeds silently.
     *
     * @param vesselId
     */
    void stopMonitoring(VesselId vesselId);

    /**
     * Returns true if the vessel is being monitored.
     *
     * @param vesselId
     * @return true or false
     */
    boolean isActive(VesselId vesselId);
}
