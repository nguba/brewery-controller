package adapter.outputs.metrics;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

class DataRecorderTest {

    /**
     * Discovery test to explore how the testcontainer framework is used to talk to an innodb image.
     */
    @Test
    void testContainerStartup() {
        try(InfluxDBContainer<?> container = new InfluxDBContainer<>(
                DockerImageName.parse("influxdb:2.7.1")
        );) {
            container.start();

            assertThat(container.isRunning()).isTrue();

            InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                    .url(container.getUrl())
                    //.authenticateToken(container.getToken())
                    .org(container.getOrganization())
                    .bucket(container.getBucket())
                    .build();

            try (InfluxDBClient client = InfluxDBClientFactory.create(options)) {

                assertThat(client.ping()).isTrue();

            }
        }
    }
}