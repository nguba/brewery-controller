package framework.adapter.output.influxdb;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;

import java.util.Objects;
import java.util.StringJoiner;

@Measurement(name = "vessel_mapping")
public class VesselMapping {

    @Column(tag = true)
    protected String vesselId;

    @Column
    protected Integer value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VesselMapping that = (VesselMapping) o;
        return Objects.equals(vesselId, that.vesselId) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vesselId, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", VesselMapping.class.getSimpleName() + "[", "]")
                .add("vesselId='" + vesselId + "'")
                .add("value=" + value)
                .toString();
    }
}
