package framework.adapter.output.influxdb;

import application.port.output.VesselOutputPort;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import domain.Vessel;
import domain.VesselId;
import junit.InfluxDbFixture;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;

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
        final Vessel vessel = Vessel.with(VesselId.ofBoilKettle());
        final VesselMapping mapping = new VesselMapping();
        mapping.vesselId = vessel.id().value();
        mapping.value = 6;

        writeRecord(mapping);
    }

    private void writeRecord(Object object) {
        client.getWriteApiBlocking().writeRecord("test-bucket", "test-org", WritePrecision.NS, "vessel,unitId=1,unitName=Vessel1,unitType=Vessel value=1.0");
    }
}

