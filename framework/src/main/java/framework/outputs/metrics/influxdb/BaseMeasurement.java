package framework.outputs.metrics.influxdb;

import com.influxdb.annotations.Column;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class BaseMeasurement {

    @Column(tag = true)
    protected String unitId;
    @Column
    protected Double value;
    @Column(timestamp = true)
    protected Instant time;

    public BaseMeasurement() {
    }

    protected BaseMeasurement(String unitId, Double value, Instant time) {
        this.unitId = unitId;
        this.value = value;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseMeasurement that = (BaseMeasurement) o;
        return Objects.equals(unitId, that.unitId) && Objects.equals(value, that.value) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitId, value, time);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProcessValue.class.getSimpleName() + "[", "]")
                .add("unitId='" + unitId + "'")
                .add("value=" + value)
                .add("time=" + time)
                .toString();
    }
}
