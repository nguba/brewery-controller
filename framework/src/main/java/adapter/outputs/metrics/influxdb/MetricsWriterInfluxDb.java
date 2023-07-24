package adapter.outputs.metrics.influxdb;

import adapter.outputs.metrics.MetricsWriter;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;

public class MetricsWriterInfluxDb implements MetricsWriter {

    private final InfluxDBClient client;
    private final String bucket;
    private final String organization;

    public MetricsWriterInfluxDb(InfluxDBClient client, String bucket, String organization) {
        this.client = client;
        this.bucket = bucket;
        this.organization = organization;
    }

    @Override
    public void record(Setpoint setpoint) {
        client.getWriteApiBlocking().writeMeasurement(bucket, organization, WritePrecision.NS, setpoint);
    }

    @Override
    public void record(ProcessValue expected) {
        client.getWriteApiBlocking().writeMeasurement(bucket, organization, WritePrecision.NS, expected);
    }

    public String bucket() {
        return bucket;
    }
}
