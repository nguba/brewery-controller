package adapter.outputs.pxu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PxuProfileTest extends PxuFixture<PxuProfile> {

    @Test
    void hasUnitId() {
        assertThat(results.unitId()).isEqualTo(UNIT_ID);
    }

    @Test
    void readSegmentZero() {
        PxuProfile.Segment actual = results.segment(0);

        assertThat(actual).isEqualTo(new PxuProfile.Segment(11, 12));
    }

    @Test
    void readSegmentOne() {
        PxuProfile.Segment actual = results.segment(1);

        assertThat(actual).isEqualTo(new PxuProfile.Segment(21, 22));
    }

    @Test
    void readSegmentTwo() {
        PxuProfile.Segment actual = results.segment(2);

        assertThat(actual).isEqualTo(new PxuProfile.Segment(31, 32));
    }

    @Test
    void readBeyondMaxSegment() {
        assertThatIllegalArgumentException().isThrownBy(() -> results.segment(15))
                .withMessage("A segment index must be between 0 and 14.");
    }

    @Override
    protected Runnable networkCommand(PxuReadListener<PxuProfile> listener) {
        return () -> pxu.queryProfile(UNIT_ID, listener);
    }
}