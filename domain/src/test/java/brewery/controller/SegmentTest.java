package brewery.controller;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SegmentTest {
    TemperatureProfile.Segment segment = makeSegment(900);

    private static TemperatureProfile.Segment makeSegment(int minutes) {
        return TemperatureProfile.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(minutes));
    }

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
                        makeSegment(901))
                .withMessage("Duration cannot exceed 900 minutes");
    }

    @Test
    void isSoakByDefault() {
        assertThat(segment.type()).isEqualTo(TemperatureProfile.Segment.Type.SOAK);
    }

    @Test
    void isRampType() {
        assertThat(segment.asRampType().type()).isEqualTo(TemperatureProfile.Segment.Type.RAMP);
    }


    @Test
    void toStringContainsSetpointDurationAndType() {
        assertThat(segment.toString()).isEqualTo("Segment[type=SOAK, setpoint=55.0 (C), duration=PT15H]");
    }
}