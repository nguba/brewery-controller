package adapter.outputs.pxu.event;

import adapter.outputs.pxu.PxuProfile;
import adapter.outputs.pxu.PxuTestConstants;
import com.ghgande.j2mod.modbus.procimg.Register;

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