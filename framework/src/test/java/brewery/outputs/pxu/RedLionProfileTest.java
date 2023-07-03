package brewery.outputs.pxu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class RedLionProfileTest extends PxuFixture<RedLionProfile> {

    @Test
    void readSegmentZero() {
        RedLionProfile.Segment actual = results.segment(0);

        assertThat(actual).isEqualTo(new RedLionProfile.Segment(11, 12));
    }

    @Test
    void readSegmentOne() {
        RedLionProfile.Segment actual = results.segment(1);

        assertThat(actual).isEqualTo(new RedLionProfile.Segment(21, 22));
    }

    @Test
    void readSegmentTwo() {
        RedLionProfile.Segment actual = results.segment(2);

        assertThat(actual).isEqualTo(new RedLionProfile.Segment(31, 32));
    }

    @Test
    void readBeyondMaxSegment() {
        assertThatIllegalArgumentException().isThrownBy(() -> results.segment(15))
                .withMessage("A segment index must be between 0 and 14.");
    }

    @Override
    protected Runnable networkCommand(RedLionNetworkListener<RedLionProfile> listener) {
        return () -> pxu.queryProfile(UNIT_ID, listener);
    }
}