package domain;

import java.time.Duration;
import java.util.Objects;

/**
 * Encapsulates the interval at which the temperature of a vessel is monitored.
 */
public class Interval {

    private final Duration interval;

    private Interval(Duration interval) {
        this.interval = interval;
    }

    public static Interval of(Duration interval) {
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        } else if (interval.isNegative()) {
            throw new IllegalArgumentException("Interval cannot be negative");
        } else if (interval.isZero()) {
            throw new IllegalArgumentException("Interval cannot be zero");
        }
        return new Interval(interval);
    }

    public long toSeconds() {
        return interval.getSeconds();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval1 = (Interval) o;
        return Objects.equals(interval, interval1.interval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interval);
    }
}
