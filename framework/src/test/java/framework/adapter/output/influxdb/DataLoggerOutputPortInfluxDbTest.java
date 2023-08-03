package framework.adapter.output.influxdb;

import application.port.output.DataLoggerOutputPort;
import com.influxdb.client.InfluxDBClient;
import domain.TemperatureControllerId;
import domain.VesselId;
import junit.InfluxDbFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("Registering a temperature controller should write a measurement")
    void registerTemperatureControllerExists() {
        port.registerTemperatureController(vesselId, temperatureControllerId);

        assertThat(port.findTemperatureControllerId(vesselId)).contains(temperatureControllerId);
    }

    @Test
    @DisplayName("Finding a non existing temperature controller should return empty optional")
    void findTemperatureControllerNonExists() {
        assertThat(port.findTemperatureControllerId(vesselId)).isEmpty();
    }
}

