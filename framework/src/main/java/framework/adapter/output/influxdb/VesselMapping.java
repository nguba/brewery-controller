package framework.adapter.output.influxdb;


import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import framework.outputs.metrics.influxdb.BaseMeasurement;

@Measurement(name = "vessel_mapping")
public class VesselMapping {

    @Column(tag = true)
    protected String vesselId;

    @Column
    protected Integer value;
}
