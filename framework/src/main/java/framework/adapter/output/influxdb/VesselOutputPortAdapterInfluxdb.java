package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.TemperatureControllerId;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;

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
        return null;
    }

    @Override
    public void saveProfile(VesselId id, TemperatureProfile profile) {
    }

    @Override
    public void addVessel(Vessel vessel) {
        client.getWriteApiBlocking().writeMeasurement("test-bucket", "test-org", WritePrecision.NS, vessel);
    }

    @Override
    public void registerTemperatureController(VesselId id, TemperatureControllerId temperatureControllerId) {

    }

    @Override
    public TemperatureControllerId findTemperatureControllerId(VesselId id) {
        return null;
    }
}
