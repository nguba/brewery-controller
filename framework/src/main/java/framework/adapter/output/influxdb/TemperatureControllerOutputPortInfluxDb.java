package framework.adapter.output.influxdb;

import application.port.output.TemperatureControllerOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.Schedule;
import domain.TemperatureControllerId;
import domain.VesselId;

import java.util.List;
import java.util.Optional;

public class TemperatureControllerOutputPortInfluxDb implements TemperatureControllerOutputPort {

    private final InfluxDBClient client;

    private TemperatureControllerOutputPortInfluxDb(InfluxDBClient client) {
        this.client = client;
    }

    public static TemperatureControllerOutputPort with(InfluxDBClient client) {
        return new TemperatureControllerOutputPortInfluxDb(client);
    }


    @Override
    public void saveProfile(VesselId id, Schedule profile) {
    }

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {
        final TemperatureControllerMeasurement measurement = TemperatureControllerMeasurement.with(id, temperatureControllerId);
        client.getWriteApiBlocking().writeMeasurement("test-bucket", "test-org", WritePrecision.NS, measurement);
    }

    @Override
    public Optional<TemperatureControllerId> findTemperatureControllerId(VesselId id) {
        String flux = "from(bucket:\"test-bucket\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"temperature_controller\")";
        List<TemperatureControllerMeasurement> controllers = client.getQueryApi().query(flux, TemperatureControllerMeasurement.class);
        return controllers.isEmpty() ? Optional.empty() : Optional.ofNullable(controllers.get(0).toDomain());
    }
}
