package application.port.input;

import application.port.output.EventPublisherOutputPort;
import application.port.output.TemperatureControllerOutputPort;
import application.usecase.VesselMonitorUseCase;
import domain.Interval;
import domain.Vessel;
import domain.VesselId;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class VesselMonitorInputPort implements VesselMonitorUseCase {
    private final TemperatureControllerOutputPort temperatureController;
    private final ScheduledExecutorService scheduledExecutorService;
    private final EventPublisherOutputPort eventPublisher;
    private final SystemConfigurationInputPort systemConfiguration;
    private final ConcurrentMap<VesselId, ScheduledFuture<?>> monitoredVessels = new java.util.concurrent.ConcurrentHashMap<>();

    public VesselMonitorInputPort(TemperatureControllerOutputPort temperatureController,
                                  ScheduledExecutorService scheduledExecutorService,
                                  EventPublisherOutputPort eventPublisher,
                                  SystemConfigurationInputPort systemConfiguration) {
        this.temperatureController = temperatureController;
        this.scheduledExecutorService = scheduledExecutorService;
        this.eventPublisher = eventPublisher;
        this.systemConfiguration = systemConfiguration;
    }

    public static VesselMonitorInputPort with(TemperatureControllerOutputPort temperatureController,
                                              ScheduledExecutorService scheduledExecutorService,
                                              EventPublisherOutputPort eventPublisher,
                                              SystemConfigurationInputPort systemConfiguration) {
        return new VesselMonitorInputPort(temperatureController, scheduledExecutorService, eventPublisher,
                systemConfiguration);
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
        Optional<Vessel> vessel = systemConfiguration.locateVessel(vesselId);
        vessel.orElseThrow(() -> new IllegalStateException("No temperature controller found for " + vesselId));

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(() -> {
            temperatureController.requestMetrics(vessel.get().temperatureControllerId(), eventPublisher);
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
