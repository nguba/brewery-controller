package domain;

import java.util.Objects;
import java.util.StringJoiner;

public class Vessel {
    private final VesselId id;
    private final TemperatureControllerId temperatureControllerId;
    private Temperature setPoint;
    private TemperatureProfile profile;

    private Vessel(VesselId id, TemperatureControllerId temperatureControllerId) {
        this.id = id;
        this.temperatureControllerId = temperatureControllerId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vessel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("temperatureControllerId=" + temperatureControllerId)
                .add("setPoint=" + setPoint)
                .add("profile=" + profile)
                .toString();
    }

    public static Vessel with(VesselId vesselId, TemperatureControllerId temperatureControllerId) {
        return new Vessel(vesselId, temperatureControllerId);
    }

    public void setPoint(Temperature setPoint) {
        this.setPoint = setPoint;
    }

    public Temperature setPoint() {
        return setPoint;
    }

    public VesselId id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vessel vessel = (Vessel) o;
        return Objects.equals(id, vessel.id) && Objects.equals(setPoint, vessel.setPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, setPoint);
    }

    public void profile(TemperatureProfile profile) {
        this.profile = profile;
    }

    public TemperatureProfile profile() {
        return profile;
    }

    public TemperatureControllerId temperatureControllerId() {
        return temperatureControllerId;
    }
}
