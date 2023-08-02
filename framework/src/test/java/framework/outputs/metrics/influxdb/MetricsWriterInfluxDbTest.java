package framework.outputs.metrics.influxdb;

import com.influxdb.client.InfluxDBClient;
import framework.outputs.metrics.MetricNames;
import junit.InfluxDbFixture;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.InfluxDBContainer;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Discovery test to explore how the <a href="https://java.testcontainers.org/">Testcontainers</a> framework is used
 * for testing the InfluxDB client.
 */
@InfluxDbFixture
class MetricsWriterInfluxDbTest {

    private final InfluxDBClient client;

    MetricsWriterInfluxDbTest(InfluxDBClient client, InfluxDBContainer<?> container) {
        this.client = client;
        writer = new MetricsWriterInfluxDb(client, container.getBucket(), container.getOrganization());
    }

    private final MetricsWriterInfluxDb writer;

    @Test
    void testContainerStartup() {
        assertThat(client.ping()).isTrue();
    }

    @Test
    void recordSetpoint() {
        Setpoint expected = Setpoint.of("6", 55D, Instant.now());

        writer.record(expected);

        List<Setpoint> results = readMetric(Setpoint.class, MetricNames.SETPOINT);
        assertThat(results).contains(expected);
    }

    @NotNull
    private <T> List<T> readMetric(Class<T> clazz, String metricName) {
        String flux = "from(bucket:\"" + writer.bucket() + "\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"" + metricName + "\")";
        return client.getQueryApi().query(flux, clazz);
    }

    @Test
    void recordProcessValue() {
        ProcessValue expected = ProcessValue.of("6", 55D, Instant.now());

        writer.record(expected);

        List<ProcessValue> results = readMetric(ProcessValue.class, MetricNames.PROCESS_VALUE);
        assertThat(results).contains(expected);
    }
}