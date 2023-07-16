package brewery.controller;

public record VesselId(int id) {
    public static VesselId of(int id) {
        return new VesselId(id);
    }
}
