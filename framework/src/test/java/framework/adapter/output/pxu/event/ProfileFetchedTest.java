package framework.adapter.output.pxu.event;

import com.ghgande.j2mod.modbus.procimg.Register;
import framework.adapter.output.pxu.PxuProfile;
import framework.adapter.output.pxu.PxuTestConstants;

class ProfileFetchedTest extends PxuEventFixture<ProfileFetched, PxuProfile> {

    @Override
    PxuProfile makeValue() {
        return new PxuProfile(new Register[0]);
    }

    @Override
    int makeUnitId() {
        return PxuTestConstants.UNIT_ID;
    }

    @Override
    ProfileFetched makeEvent(int unitId, PxuProfile value, long timeStamp) {
        return new ProfileFetched(unitId, value, timeStamp);
    }
}