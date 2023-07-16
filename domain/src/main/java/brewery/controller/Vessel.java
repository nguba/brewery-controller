package brewery.controller;

public class Vessel {
    private final VesselId vesselId;
    private Temperature setPoint;

    private Vessel(VesselId vesselId) {
        this.vesselId = vesselId;
    }

    public static Vessel with(VesselId vesselId) {
        return new Vessel(vesselId);
    }

    public VesselId vesselId() {
        return vesselId;
    }

    public void setPoint(Temperature setPoint) {
        this.setPoint = setPoint;
    }

    public Temperature setPoint() {
        return setPoint;
    }

    @Override
    public String toString() {
        return "Vessel{" +
                "vesselId=" + vesselId +
                ", setPoint=" + setPoint +
                '}';
    }

    public VesselId id() {
        return vesselId;
    }
}
