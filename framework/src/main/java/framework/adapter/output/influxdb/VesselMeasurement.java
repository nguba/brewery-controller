package framework.adapter.output.influxdb;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import domain.TemperatureControllerId;
import domain.Vessel;
import domain.VesselId;

import java.util.Objects;
import java.util.StringJoiner;

@Measurement(name = "vessel")
public class VesselMeasurement {

    @Column(tag = true)
    protected String vesselId;

    @Column
    protected Integer value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VesselMeasurement that = (VesselMeasurement) o;
        return Objects.equals(vesselId, that.vesselId) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vesselId, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VesselMeasurement.class.getSimpleName() + "[", "]")
                .add("vesselId='" + vesselId + "'")
                .add("value=" + value)
                .toString();
    }

    public Vessel toDomain() {
        return Vessel.with(VesselId.of(vesselId), TemperatureControllerId.of(value));
    }
}
