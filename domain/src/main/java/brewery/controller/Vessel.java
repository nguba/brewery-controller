package brewery.controller;

import java.util.StringJoiner;

public class Vessel {
    private final VesselId id;
    private Temperature setPoint;

    private Vessel(VesselId id) {
        this.id = id;
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
}
