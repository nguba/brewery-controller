package domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

class ScheduleTest {
    final Schedule schedule = Schedule.create("Test Schedule");

    @Test
    void addSegment() {
        Schedule.Segment segment = makeSegment();
        schedule.addSegment(segment);
        assertThat(schedule.segments()).containsExactly(segment);
    }

    private static Schedule.Segment makeSegment() {
        return Schedule.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30));
    }

    @Test
    void addSegmentBeyondMaxLimit() {
        for (int i = 0; i < 15; i++) {
            Schedule.Segment segment = makeSegment();
            schedule.addSegment(segment);
        }

        assertThatIndexOutOfBoundsException().isThrownBy(() -> {
            Schedule.Segment segment = makeSegment();
            schedule.addSegment(segment);
        }).withMessage("A profile cannot contain more than 15 segments");
    }

    @Test
    void clearProfile() {
        Schedule.Segment segment = makeSegment();
        schedule.addSegment(segment);
        schedule.clear();

        assertThat(schedule.segments()).isEmpty();
    }

    @Test
    void addingMultipleSegmentsWillAssignCorrectType() {
        schedule.addSegment(Schedule.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30)));
        schedule.addSegment(Schedule.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(10)));
        schedule.addSegment(Schedule.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(30)));
        schedule.addSegment(Schedule.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(10)));
        schedule.addSegment(Schedule.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(30)));

        List<Schedule.Segment> expected = new ArrayList<>();
        expected.add(Schedule.Segment.with(Temperature.inCelsius(35.0), Duration.ofMinutes(30)));
        expected.add(Schedule.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(10)).asRampType());
        expected.add(Schedule.Segment.with(Temperature.inCelsius(45.0), Duration.ofMinutes(30)));
        expected.add(Schedule.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(10)).asRampType());
        expected.add(Schedule.Segment.with(Temperature.inCelsius(55.0), Duration.ofMinutes(30)));

        assertThat(schedule.segments()).containsExactlyElementsOf(expected);
    }

    @Test
    void toStringHasExpectedFields() {
        assertThat(schedule.toString()).isEqualTo("Schedule[segments=[]]");
    }
}