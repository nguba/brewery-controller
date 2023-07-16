package adapter.outputs.pxu.event;

class PxuRequestFailedTest extends PxuEventFixture<PxuRequestFailed, Exception> {


    @Override
    Exception makeValue() {
        return new Exception("test exception");
    }

    @Override
    PxuRequestFailed makeEvent(int unitId, Exception value, long timeStamp) {
        return new PxuRequestFailed(unitId, timeStamp, value);
    }

    @Override
    int makeUnitId() {
        return 25;
    }
}