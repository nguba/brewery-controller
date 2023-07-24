package domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

class TemperatureProfileTest {
    final TemperatureProfile temperatureProfile = TemperatureProfile.create();

    @Test
    void addSegment() {
        TemperatureProfile.Segment segment = makeSegment();
        temperatureProfile.addSegment(segment);
        assertThat(temperatureProfile.segments()).containsExactly(segment);
    }

    private static TemperatureProfile.Segment makeSegment() {
        return TemperatureProfile.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));
    }

    @Test
    void addSegmentBeyondMaxLimit() {
        for(int i = 0; i < 15; i++) {
            TemperatureProfile.Segment segment = makeSegment();
            temperatureProfile.addSegment(segment);
        }

        assertThatIndexOutOfBoundsException().isThrownBy(() -> {
            TemperatureProfile.Segment segment = makeSegment();
            temperatureProfile.addSegment(segment);
        }).withMessage("A profile cannot contain more than 15 segments");
    }

    @Test
    void clearProfile() {
        TemperatureProfile.Segment segment = makeSegment();
        temperatureProfile.addSegment(segment);
        temperatureProfile.clear();

        assertThat(temperatureProfile.segments()).isEmpty();
    }

    @Test
    void addingMultipleSegmentsWillAssignCorrectType() {
        temperatureProfile.addSegment(TemperatureProfile.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30)));
        temperatureProfile.addSegment(TemperatureProfile.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(10)));
        temperatureProfile.addSegment(TemperatureProfile.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(30)));
        temperatureProfile.addSegment(TemperatureProfile.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(10)));
        temperatureProfile.addSegment(TemperatureProfile.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(30)));

        List<TemperatureProfile.Segment> expected = new ArrayList<>();
        expected.add(TemperatureProfile.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30)));
        expected.add(TemperatureProfile.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(10)).asRampType());
        expected.add(TemperatureProfile.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(30)));
        expected.add(TemperatureProfile.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(10)).asRampType());
        expected.add(TemperatureProfile.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(30)));

        assertThat(temperatureProfile.segments()).containsExactlyElementsOf(expected);
    }

    @Test
    void toStringHasExpectedFields() {
        assertThat(temperatureProfile.toString()).isEqualTo("TemperatureProfile[segments=[]]");
    }
}