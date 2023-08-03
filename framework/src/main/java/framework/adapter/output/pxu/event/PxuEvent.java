package framework.adapter.output.pxu.event;

public interface PxuEvent<T> {
    long timeStamp();

    T value();

    int unitId();
}
