package adapter.outputs.metrics;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.domain.WritePrecision;
import junit.extension.InfluxdbClientExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.InfluxDBContainer;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

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

    @Test
    void sampleWrite(InfluxDBContainer<?> container) {

        final Temperature temperature = new Temperature();
        temperature.unitId = "6";
        temperature.value = 55D;
        temperature.time = Instant.now();

        client.getWriteApiBlocking().writeMeasurement(container.getBucket(), container.getOrganization(), WritePrecision.NS, temperature);

        String flux = "from(bucket:\"" + container.getBucket() + "\") |> range(start: 0) |> filter(fn: (r) => r._measurement == \"temperature\")";

        QueryApi queryApi = client.getQueryApi();


        List<Temperature> query = queryApi.query(flux, Temperature.class);
        assertThat(query).contains(temperature);
        query.forEach(System.out::println);
    }

    @Measurement(name = "temperature")
    public static class Temperature {

        @Column(tag = true)
        String unitId;

        @Column
        Double value;

        @Column(timestamp = true)
        Instant time;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Temperature that = (Temperature) o;
            return Objects.equals(unitId, that.unitId) && Objects.equals(value, that.value) && Objects.equals(time, that.time);
        }

        @Override
        public int hashCode() {
            return Objects.hash(unitId, value, time);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Temperature.class.getSimpleName() + "[", "]")
                    .add("unitId='" + unitId + "'")
                    .add("value=" + value)
                    .add("time=" + time)
                    .toString();
        }
    }
}