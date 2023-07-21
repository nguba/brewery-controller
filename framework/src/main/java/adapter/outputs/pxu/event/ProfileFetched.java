package adapter.outputs.pxu.event;

import adapter.outputs.pxu.PxuProfile;

import java.util.StringJoiner;

public record ProfileFetched(int unitId, PxuProfile value, long timeStamp) implements PxuEvent<PxuProfile> {

    public static ProfileFetched of(int unitId, PxuProfile profile) {
        return new ProfileFetched(unitId, profile, System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProfileFetched.class.getSimpleName() + "[", "]")
                .add("unitId=" + unitId)
                .add("value=" + value)
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
