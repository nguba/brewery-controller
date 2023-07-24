package adapter.outputs.metrics;

import adapter.outputs.metrics.influxdb.ProcessValue;
import adapter.outputs.metrics.influxdb.Setpoint;
import adapter.outputs.pxu.PxuMetrics;

/**
 * The MetricsWriter is responsible for recording the metrics of the PXU.
 * These are important for graphing the temperature profiles of each step in the brewing process.
 * @see PxuMetrics
 */
public interface MetricsWriter {

    void record(Setpoint setpoint);

    void record(ProcessValue expected);
}
