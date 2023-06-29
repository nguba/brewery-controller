package brewery.controller;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

class TemperatureProfileTest {
    TemperatureProfile temperatureProfile = new TemperatureProfile();

    @Test
    void addSegment() {

        Segment segment = Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));

        temperatureProfile.addSegment(segment);

        assertThat(temperatureProfile.segments()).containsExactly(segment);
    }

    @Test
    void addSegmentBeyondMaxLimit() {
        for(int i = 0; i < 15; i++) {
            Segment segment = Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));
            temperatureProfile.addSegment(segment);
        }

        assertThatIndexOutOfBoundsException().isThrownBy(() -> {
            Segment segment = Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));
            temperatureProfile.addSegment(segment);
        }).withMessage("A profile cannot contain more than 15 segments");
    }

    @Test
    void clearProfile() {
        Segment segment = Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));

        temperatureProfile.addSegment(segment);
        temperatureProfile.clear();

        assertThat(temperatureProfile.segments()).isEmpty();
    }
}