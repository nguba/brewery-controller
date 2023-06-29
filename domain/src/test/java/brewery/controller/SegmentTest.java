package brewery.controller;

import org.assertj.core.api.ThrowableAssertAlternative;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


class SegmentTest {
    Segment segment = Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(900));

    @Test
    void hasSetpoint() {
        assertThat(segment.setpoint()).isEqualTo(Temperature.inCelsius(55.0));
    }

    @Test
    void hasDuration() {
        assertThat(segment.duration()).isEqualTo(Duration.ofMinutes(900));
    }

    @Test
    void durationCannotBeLongerThan900Minutes() {
        assertThatIllegalArgumentException().isThrownBy(() ->
                Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(901)))
                .withMessage("Duration cannot exceed 900 minutes");
    }
}