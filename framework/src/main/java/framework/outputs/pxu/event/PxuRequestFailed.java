package framework.outputs.pxu.event;

import java.util.StringJoiner;

public record PxuRequestFailed(int unitId, long timeStamp, Exception value) implements PxuEvent<Exception> {

    public static PxuRequestFailed of(int unitId, Exception exception) {
        return new PxuRequestFailed(unitId, System.currentTimeMillis(), exception);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PxuRequestFailed.class.getSimpleName() + "[", "]")
                .add("unitId=" + unitId)
                .add("timeStamp=" + timeStamp)
                .add("value=" + value)
                .toString();
    }
}
