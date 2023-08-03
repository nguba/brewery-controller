package framework.adapter.output.pxu.event;

import com.ghgande.j2mod.modbus.procimg.Register;
import framework.adapter.output.pxu.PxuMetrics;
import framework.adapter.output.pxu.PxuTestConstants;

class MetricsFetchedTest extends PxuEventFixture<MetricsFetched, PxuMetrics> {

    @Override
    PxuMetrics makeValue() {
        return new PxuMetrics(new Register[0]);
    }

    @Override
    MetricsFetched makeEvent(int unitId, PxuMetrics value, long timeStamp) {
        return new MetricsFetched(unitId, value, timeStamp);
    }

    @Override
    int makeUnitId() {
        return PxuTestConstants.UNIT_ID;
    }
}