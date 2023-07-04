package brewery.controller;

import java.time.Duration;

// list of segments containing the setpoint and the time.
public class Segment {
    private final Temperature setpoint;
    private final Duration duration;

    private Segment(Temperature setpoint, Duration duration) {
        this.setpoint = setpoint;
        this.duration = duration;
    }

    public static Segment with(Temperature setpoint, Duration duration) {
        // this rule is enforced by the PXU - it should probably be handled in the framework output.
        if(duration.toMinutes() > 900) // this is a controller limitation, not a domain limitation
            throw new IllegalArgumentException("Duration cannot exceed 900 minutes");
        
        return new Segment(setpoint, duration);
    }

    public Temperature setpoint() {
        return setpoint;
    }

    public Duration duration() {
        return duration;
    }
}
