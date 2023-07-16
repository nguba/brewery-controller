package adapter.outputs.pxu;

import adapter.outputs.pxu.event.ProfileFetched;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import junit.AsyncTestUtils;
import junit.PxuTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@PxuTest
class PxuProfileTest {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PxuProfileTest.class);

    private final PxuNetwork pxu;

    PxuProfileTest(PxuNetwork pxu) {
        this.pxu = pxu;
    }

    private PxuProfile results;

    private final AsyncTestUtils asyncTestUtils = new AsyncTestUtils();

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EventBus eventBus;

    @BeforeEach
    void setUp() throws InterruptedException {
        eventBus.register(this);
        asyncTestUtils.executeAndWait(() -> pxu.queryProfile(PxuTestConstants.UNIT_ID), 30);
    }

    @Subscribe
    public void profileAvailable(ProfileFetched event) {
        LOGGER.info("{}", event);
        results = event.value();
        asyncTestUtils.notifySuccess();
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
}