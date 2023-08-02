package domain;

import java.time.Duration;
import java.util.*;

/**
 * <p>
 * A temperature profile is a list of segments containing the setpoint and the time.
 * </p>
 * <p>
 * The segments are executed in order.  A temperature profile is executed by the PID controller.
 * It can contain up to 15 segments max.
 * </p>
 */
public class TemperatureProfile {
    private final List<Segment> segments = new ArrayList<>(15);

    /**
     * Adds a segment to the profile.
     *
     * @param segment the segment to add
     * @throws IndexOutOfBoundsException if the profile already contains 15 segments
     */
    public void addSegment(Segment segment) {
        if(segments.size() >= 15)
            throw new IndexOutOfBoundsException("A profile cannot contain more than 15 segments");

        if(!segments.isEmpty()) {
            Segment previousSegment = segments.get(segments.size() - 1);
            if(previousSegment.type().equals(Segment.Type.SOAK))
                segments.add(segment.asRampType());
            else
                segments.add(segment);
        }
        else
            segments.add(segment);
    }

    private TemperatureProfile() {
    }

    public static TemperatureProfile create() {
        return new TemperatureProfile();
    }

    /**
     * Returns an unmodifiable view of the segments.
     *
     * @return a list of segments
     */
    public List<Segment> segments() {
        return Collections.unmodifiableList(segments);
    }

    public void clear() {
        segments.clear();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemperatureProfile.class.getSimpleName() + "[", "]")
                .add("segments=" + segments)
                .toString();
    }

    // list of segments containing the setpoint and the time.
    public static class Segment {
        private final Type type;

        public Segment asRampType() {
            return new Segment(setpoint, duration, Type.RAMP);
        }

        enum Type {
            SOAK, RAMP
        }
        private final Temperature setpoint;
        private final Duration duration;

        private Segment(Temperature setpoint, Duration duration, Type type) {
            this.setpoint = setpoint;
            this.duration = duration;
            this.type = type;
        }

        public static Segment with(Temperature setpoint, Duration duration) {
            validateDuration(duration);
            return new Segment(setpoint, duration, Type.SOAK);
        }

        // this rule is enforced by the PXU - a segment cannot be longer than 900 minutes
        private static void validateDuration(Duration duration) {
            if(duration.toMinutes() > 900) // this is a controller limitation, not a domain limitation
                throw new IllegalArgumentException("Duration cannot exceed 900 minutes");
        }

        public Temperature setpoint() {
            return setpoint;
        }

        public Duration duration() {
            return duration;
        }

        public Type type() {
            return type;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Segment.class.getSimpleName() + "[", "]")
                    .add("type=" + type)
                    .add("setpoint=" + setpoint)
                    .add("duration=" + duration)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Segment segment = (Segment) o;
            return type == segment.type && Objects.equals(setpoint, segment.setpoint) && Objects.equals(duration, segment.duration);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, setpoint, duration);
        }
    }
}
