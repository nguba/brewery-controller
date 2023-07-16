package adapter.outputs.pxu.event;

import adapter.outputs.pxu.PxuMetrics;

public record MetricsFetched(int unitId, PxuMetrics value, long timeStamp) implements PxuEvent<PxuMetrics> {

    public static MetricsFetched of(int unitId, PxuMetrics metrics) {
        return new MetricsFetched(unitId, metrics, System.currentTimeMillis());
    }
}
