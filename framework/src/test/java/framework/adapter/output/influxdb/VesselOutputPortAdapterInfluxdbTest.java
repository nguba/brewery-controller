package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.Vessel;
import domain.VesselId;
import junit.InfluxDbFixture;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@InfluxDbFixture
class VesselOutputPortAdapterInfluxdbTest {

    static VesselOutputPort port;

    private final InfluxDBClient client;

    public VesselOutputPortAdapterInfluxdbTest(InfluxDBClient client, InfluxDBContainer<?> container) {
        this.client = client;
        port = VesselOutputPortAdapterInfluxdb.with(client);
    }

    @Test
    void findVessel() {
        final Vessel vessel = Vessel.with(VesselId.of("Test Vessel"));
        final VesselMapping mapping = new VesselMapping();
        mapping.vesselId = vessel.id().value();
        mapping.value = 6;

        write(mapping);

        final List<VesselMapping> vessels = find(VesselMapping.class, "vessel_mapping");
        System.out.println(vessels);

        assertThat(vessels).containsExactly(mapping);
    }

    private void write(Object object) {
        client.getWriteApiBlocking().writeMeasurement("test-bucket", "test-org", WritePrecision.NS, object);
    }

    private <T> List<T> find(Class<T> clazz, String measurementName) {
        String flux = "from(bucket:\"test-bucket\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"" + measurementName + "\")";
        return client.getQueryApi().query(flux, clazz);
    }
}

