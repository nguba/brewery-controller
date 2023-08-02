package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VesselTest {

    Vessel vessel = Vessel.with(VesselId.ofBoilKettle(), TemperatureControllerId.of(1));

    @Test
    @DisplayName("can access the setpoint value")
    void hasSetpoint() {
        Temperature expectedTemperature = Temperature.inCelsius(62);
        vessel.setPoint(expectedTemperature);

        assertThat(vessel.setPoint()).isEqualTo(expectedTemperature);
    }

    @Test
    @DisplayName("toString contains expected information")
    void toStringHasExpectedFields() {
        assertThat(vessel.toString())
                .isEqualTo("Vessel[id=VesselId[value=BoilKettle], temperatureControllerId=TemperatureControllerId[value=1], setPoint=null, schedule=null]");
    }

    @Test
    @DisplayName("can access the temperature profile")
    void hasProfile() {
        Schedule expected = Schedule.create("Test Schedule");
        vessel.profile(expected);

        assertThat(vessel.profile()).isEqualTo(expected);
    }

    @Test
    @DisplayName("can access the temperature controller id")
    void hasTemperatureControllerId() {
        assertThat(vessel.temperatureControllerId()).isEqualTo(TemperatureControllerId.of(1));
    }
}