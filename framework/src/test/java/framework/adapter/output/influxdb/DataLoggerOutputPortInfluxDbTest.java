package framework.adapter.output.influxdb;

import application.port.output.DataLoggerOutputPort;
import com.influxdb.client.InfluxDBClient;
import domain.TemperatureControllerId;
import domain.VesselId;
import junit.InfluxDbFixture;
import org.testcontainers.containers.InfluxDBContainer;

@InfluxDbFixture
class DataLoggerOutputPortInfluxDbTest {

    static DataLoggerOutputPort port;

    private final InfluxDBClient client;

    VesselId vesselId = VesselId.of("Test Vessel");

    TemperatureControllerId temperatureControllerId = TemperatureControllerId.of(1);

    public DataLoggerOutputPortInfluxDbTest(InfluxDBClient client, InfluxDBContainer<?> container) {
        this.client = client;
        port = DataLoggerOutputPortInfluxDb.with(client);
    }
}

