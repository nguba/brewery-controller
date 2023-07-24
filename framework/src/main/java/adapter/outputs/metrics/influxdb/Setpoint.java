package adapter.outputs.metrics.influxdb;

import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "setpoint")
public class Setpoint extends BaseMeasurement {

    /**
     * Required by influxdb client serialization
     */
    public Setpoint() {
    }

    private Setpoint(String unitId, Double value, Instant time) {
        super(unitId, value, time);
    }

    public static Setpoint of(String unitId, Double value, Instant time) {
        return new Setpoint(unitId, value, time);
    }
}
