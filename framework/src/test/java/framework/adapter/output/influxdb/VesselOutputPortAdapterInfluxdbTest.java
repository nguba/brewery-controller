package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.TemperatureControllerId;
import domain.Vessel;
import domain.VesselId;
import junit.InfluxDbFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@InfluxDbFixture
class VesselOutputPortAdapterInfluxdbTest {

    static VesselOutputPort port;

    private final InfluxDBClient client;

    final Vessel vessel = Vessel.with(VesselId.of("Test Vessel"), TemperatureControllerId.of(1));

    public VesselOutputPortAdapterInfluxdbTest(InfluxDBClient client, InfluxDBContainer<?> container) {
        this.client = client;
        port = VesselOutputPortAdapterInfluxdb.with(client);
    }

    @Test
    @DisplayName("Adding a vessel should write a measurement which can be found")
    void findVesselExists() {
        port.addVessel(vessel);

        assertThat(port.findVessel(vessel.id()).get()).isEqualTo(vessel);
    }

    @Test
    @DisplayName("Finding a non existing vessel should return empty optional")
    void findVesselNotExists() {
        assertThat(port.findVessel(vessel.id())).isEmpty();
    }

    private void write(Object object) {
        client.getWriteApiBlocking().writeMeasurement("test-bucket", "test-org", WritePrecision.NS, object);
    }

    private <T> List<T> find(Class<T> clazz, String measurementName) {
        String flux = "from(bucket:\"test-bucket\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"" + measurementName + "\")";
        return client.getQueryApi().query(flux, clazz);
    }
}

