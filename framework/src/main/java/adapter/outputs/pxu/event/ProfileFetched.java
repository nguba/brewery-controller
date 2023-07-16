package adapter.outputs.pxu.event;

import adapter.outputs.pxu.PxuProfile;

public record ProfileFetched(int unitId, PxuProfile value, long timeStamp) implements PxuEvent<PxuProfile> {

    public static ProfileFetched of(int unitId, PxuProfile profile) {
        return new ProfileFetched(unitId, profile, System.currentTimeMillis());
    }
}
