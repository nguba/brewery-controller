package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.TemperatureControllerId;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

import java.util.List;
import java.util.Optional;

public class VesselOutputPortAdapterInfluxdb implements VesselOutputPort {

    private final InfluxDBClient client;

    private VesselOutputPortAdapterInfluxdb(InfluxDBClient client) {
        this.client = client;
    }

    public static VesselOutputPort with(InfluxDBClient client) {
        return new VesselOutputPortAdapterInfluxdb(client);
    }

    @Override
    public Optional<Vessel> findVessel(VesselId id) {
        String flux = "from(bucket:\"test-bucket\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"vessel\")";
        List<VesselMeasurement> vessels = client.getQueryApi().query(flux, VesselMeasurement.class);

        return vessels.isEmpty() ? Optional.empty() : Optional.ofNullable(vessels.get(0).toDomain());
    }

    @Override
    public void saveProfile(VesselId id, TemperatureProfile profile) {
    }

    @Override
    public void addVessel(Vessel vessel) {
        final VesselMeasurement measurement = new VesselMeasurement();
        measurement.vesselId = vessel.id().value();
        measurement.value = 6;

        client.getWriteApiBlocking().writeMeasurement("test-bucket", "test-org", WritePrecision.NS, measurement);
    }

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {

    }

    @Override
    public TemperatureControllerId findTemperatureControllerId(VesselId id) {
        return null;
    }
}
