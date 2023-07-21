package adapter.outputs.metrics;

import com.influxdb.client.InfluxDBClient;
import junit.extension.InfluxdbClientExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Discovery test to explore how the <a href="https://java.testcontainers.org/">Testcontainers</a> framework is used
 * for testing the InfluxDB client.
 */
@ExtendWith({InfluxdbClientExtension.class})
class DataRecorderTest {

    private final InfluxDBClient client;

    DataRecorderTest(InfluxDBClient client) {
        this.client = client;
    }

    @Test
    void testContainerStartup() {
        assertThat(client.ping()).isTrue();
    }
}