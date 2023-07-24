package framework.outputs.metrics;

import framework.outputs.metrics.influxdb.ProcessValue;
import framework.outputs.metrics.influxdb.Setpoint;
import framework.outputs.pxu.PxuMetrics;

/**
 * The MetricsWriter is responsible for recording the metrics of the PXU.
 * These are important for graphing the temperature profiles of each step in the brewing process.
 * @see PxuMetrics
 */
public interface MetricsWriter {

    void record(Setpoint setpoint);

    void record(ProcessValue expected);
}
