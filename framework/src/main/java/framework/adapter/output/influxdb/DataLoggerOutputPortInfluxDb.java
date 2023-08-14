package framework.adapter.output.influxdb;

import application.port.output.DataLoggerOutputPort;
import com.influxdb.client.InfluxDBClient;
import domain.Schedule;
import domain.VesselId;

public class DataLoggerOutputPortInfluxDb implements DataLoggerOutputPort {

    private final InfluxDBClient client;

    private DataLoggerOutputPortInfluxDb(InfluxDBClient client) {
        this.client = client;
    }

    public static DataLoggerOutputPort with(InfluxDBClient client) {
        return new DataLoggerOutputPortInfluxDb(client);
    }


    @Override
    public void saveProfile(VesselId id, Schedule profile) {
    }
}
