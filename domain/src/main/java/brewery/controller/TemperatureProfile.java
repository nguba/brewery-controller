package brewery.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public void addSegment(Segment segment) {
        if(segments.size() >= 15)
            throw new IndexOutOfBoundsException("A profile cannot contain more than 15 segments");
        segments.add(segment);
    }

    public List<Segment> segments() {
        return new ArrayList<>(segments);
    }

    public void clear() {
        segments.clear();
    }
}
