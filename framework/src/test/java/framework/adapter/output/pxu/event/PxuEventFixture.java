package framework.adapter.output.pxu.event;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PxuEventFixture<E extends PxuEvent<V>, V> {

    private final long timeStamp = System.currentTimeMillis();

    abstract V makeValue();

    abstract E makeEvent(int unitId, V value, long timeStamp);

    abstract int makeUnitId();

    private final int unitId = makeUnitId();

    private final V value = makeValue();

    private E event;

    @BeforeEach
    void setUp() {
        event = makeEvent(unitId, value, timeStamp);
    }

    @Test
    public void hasUnitId() {
        assertEquals(unitId, event.unitId());
    }

    @Test
    public void hasValue() {
        assertEquals(value, event.value());
    }

    @Test
    public void hasTimestamp() {
        assertEquals(timeStamp, event.timeStamp());
    }

}
