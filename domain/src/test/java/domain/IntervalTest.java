package domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class IntervalTest {

    @Test
    @DisplayName("Verify that MonitorInterval has equals and hashCode implemented correctly")
    void hasEquality() {
        EqualsVerifier.forClass(Interval.class).usingGetClass().verify();
    }

    @Test
    @DisplayName("Verify that a null duration throws an exception")
    void nullDuration() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Interval.of(null))
                .withMessage("Interval cannot be null");
    }

    @Test
    @DisplayName("Verify that a negative duration throws an exception")
    void negativeDuration() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Interval.of(Duration.ofSeconds(-1)))
                .withMessage("Interval cannot be negative");
    }

    @Test
    @DisplayName("Verify that a duration is not Zero")
    void zeroDuration() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Interval.of(Duration.ofSeconds(0)))
                .withMessage("Interval cannot be zero");
    }

    @Test
    @DisplayName("Ensure we can obtain the correct value in seconds")
    void toSeconds() {
        Interval interval = Interval.of(Duration.ofSeconds(1));
        assertThat(interval.toSeconds()).isEqualTo(1);
    }
}