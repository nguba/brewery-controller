package framework.adapter.output.influxdb;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.Objects;
import java.util.StringJoiner;

@Measurement(name = "temperature_controller")
public class TemperatureControllerMeasurement {

    @Column(tag = true)
    protected String vesselId;

    @Column
    protected Integer value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureControllerMeasurement that = (TemperatureControllerMeasurement) o;
        return Objects.equals(vesselId, that.vesselId) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vesselId, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemperatureControllerMeasurement.class.getSimpleName() + "[", "]")
                .add("vesselId='" + vesselId + "'")
                .add("value=" + value)
                .toString();
    }

    public static TemperatureControllerMeasurement with(VesselId vesselId, TemperatureControllerId temperatureControllerId) {
        TemperatureControllerMeasurement temperatureControllerMeasurement = new TemperatureControllerMeasurement();
        temperatureControllerMeasurement.vesselId = vesselId.value();
        temperatureControllerMeasurement.value = temperatureControllerId.value();
        return temperatureControllerMeasurement;
    }

    public TemperatureControllerId toDomain() {
        return TemperatureControllerId.of(value);
    }
}
