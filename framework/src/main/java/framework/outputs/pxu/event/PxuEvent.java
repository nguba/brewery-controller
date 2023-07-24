package framework.outputs.pxu.event;

public interface PxuEvent<T> {
    long timeStamp();

    T value();

    int unitId();
}
