package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
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
    public Optional<Vessel> fetchVessel(VesselId id) {
        return null;
    }

    @Override
    public void saveProfile(VesselId id, TemperatureProfile profile) {

    }

    @Override
    public void addMapping(VesselId id, int unitId) {

    }
}
