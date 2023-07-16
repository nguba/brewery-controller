package adapter.outputs.pxu.event;

public record PxuRequestFailed(int unitId, long timeStamp, Exception value) implements PxuEvent<Exception> {

    public static PxuRequestFailed of(int unitId, Exception exception) {
        return new PxuRequestFailed(unitId, System.currentTimeMillis(), exception);
    }
}
