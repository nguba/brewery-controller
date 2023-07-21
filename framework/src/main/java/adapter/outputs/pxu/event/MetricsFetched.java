package adapter.outputs.pxu.event;

import adapter.outputs.pxu.PxuMetrics;

import java.util.StringJoiner;

public record MetricsFetched(int unitId, PxuMetrics value, long timeStamp) implements PxuEvent<PxuMetrics> {

    public static MetricsFetched of(int unitId, PxuMetrics metrics) {
        return new MetricsFetched(unitId, metrics, System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MetricsFetched.class.getSimpleName() + "[", "]")
                .add("unitId=" + unitId)
                .add("value=" + value)
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
