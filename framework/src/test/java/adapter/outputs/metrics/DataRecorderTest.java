package adapter.outputs.metrics;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

class DataRecorderTest {

    /**
     * Discovery test to explore how the testcontainer framework is used to talk to an innodb image.
     */
    @Test
    void testContainerStartup() {
        InfluxDBContainer<?> container = new InfluxDBContainer<>(
                DockerImageName.parse("influxdb:2.7.1")
        );
        container.start();
    }
}