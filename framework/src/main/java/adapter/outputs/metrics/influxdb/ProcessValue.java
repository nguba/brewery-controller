package adapter.outputs.metrics.influxdb;

import com.influxdb.annotations.Measurement;

import java.time.Instant;

@Measurement(name = "processValue")
public class ProcessValue extends BaseMeasurement {

    /**
     * Required by influxdb client serialization
     */
    public ProcessValue() {
    }

    private ProcessValue(String unitId, Double value, Instant time) {
        super(unitId, value, time);
    }

    public static ProcessValue of(String unitId, Double value, Instant time) {
        return new ProcessValue(unitId, value, time);
    }
}
