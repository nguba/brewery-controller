package brewery.controller;

public record VesselId(String value) {
    public static VesselId of(String value) {
        return new VesselId(value);
    }
}
