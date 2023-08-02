package domain;

public record TemperatureControllerId(int value) {

    public static TemperatureControllerId of(int value) {
        return new TemperatureControllerId(value);
    }
}
