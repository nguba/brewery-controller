package brewery.controller;

import java.util.Objects;
import java.util.StringJoiner;

public class Vessel {
    private final VesselId id;
    private Temperature setPoint;

    private Vessel(VesselId id) {
        this.id = id;
    }

    public static Vessel asHotLiquorTun() {
        return new Vessel(VesselId.of("HotLiquorTun"));
    }

    public static Vessel asBoilKettle() {
        return new Vessel(VesselId.of("BoilKettle"));
    }

    public static Vessel asFermenter() {
        return new Vessel(VesselId.of("Fermenter"));
    }

    public static Vessel asConditioningTank() {
        return new Vessel(VesselId.of("ConditioningTank"));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vessel.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("setPoint=" + setPoint)
                .toString();
    }

    public static Vessel with(VesselId id) {
        return new Vessel(id);
    }

    public static Vessel asMashTun() {
        return new Vessel(VesselId.of("MashTun"));
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
}
