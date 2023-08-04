package application.port.input;

import application.port.output.EventPublisherOutputPort;
import application.port.output.TemperatureControllerOutputPort;
import application.usecase.VesselMonitorUseCase;
import domain.Interval;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class VesselMonitorInputPort implements VesselMonitorUseCase {
    private final TemperatureControllerOutputPort temperatureControllerOutputPort;
    private final ScheduledExecutorService scheduledExecutorService;
    private final EventPublisherOutputPort eventPublisherOutputPort;
    private final ConcurrentMap<VesselId, ScheduledFuture<?>> monitoredVessels = new java.util.concurrent.ConcurrentHashMap<>();

    public VesselMonitorInputPort(TemperatureControllerOutputPort temperatureControllerOutputPort,
                                  ScheduledExecutorService scheduledExecutorService,
                                  EventPublisherOutputPort eventPublisherOutputPort) {
        this.temperatureControllerOutputPort = temperatureControllerOutputPort;
        this.scheduledExecutorService = scheduledExecutorService;
        this.eventPublisherOutputPort = eventPublisherOutputPort;
    }

    public static VesselMonitorInputPort with(TemperatureControllerOutputPort temperatureControllerOutputPort,
                                              ScheduledExecutorService scheduledExecutorService,
                                              EventPublisherOutputPort eventPublisherOutputPort) {
        return new VesselMonitorInputPort(temperatureControllerOutputPort, scheduledExecutorService, eventPublisherOutputPort);
    }

    @Override
    public void startMonitoring(VesselId vesselId, Interval interval) {
        if (vesselId == null) {
            throw new IllegalArgumentException("Vessel id cannot be null");
        }
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        }
        if (monitoredVessels.containsKey(vesselId)) {
            throw new IllegalArgumentException("Vessel is already being monitored");
        }
        TemperatureControllerId temperatureControllerId = temperatureControllerOutputPort.findTemperatureControllerId(vesselId)
                .orElseThrow(() -> new IllegalStateException("No temperature controller found for " + vesselId));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            temperatureControllerOutputPort.requestMetrics(temperatureControllerId, eventPublisherOutputPort);
        }, 0, interval.toSeconds(), TimeUnit.SECONDS);

        monitoredVessels.put(vesselId, scheduledFuture);
    }

    @Override
    public void stopMonitoring(VesselId vesselId) {
        if (vesselId == null) {
            throw new IllegalArgumentException("Vessel id cannot be null");
        }
        if (isActive(vesselId)) {
            ScheduledFuture<?> scheduledFuture = monitoredVessels.remove(vesselId);
            if (scheduledFuture != null)
                scheduledFuture.cancel(true);
        }
    }

    @Override
    public boolean isActive(VesselId vesselId) {
        return monitoredVessels.containsKey(vesselId);
    }
}
