package domain;

public record VesselId(String value) {
    public static VesselId of(String value) {
        return new VesselId(value);
    }

    public static VesselId ofHotLiquorTun() {
        return VesselId.of("HotLiquorTun");
    }

    public static VesselId ofBoilKettle() {
        return VesselId.of("BoilKettle");
    }

    public static VesselId ofFermenter() {
        return VesselId.of("Fermenter");
    }

    public static VesselId ofConditioningTank() {
        return VesselId.of("ConditioningTank");
    }

    public static VesselId ofMashTun() {
        return VesselId.of("MashTun");
    }
}
