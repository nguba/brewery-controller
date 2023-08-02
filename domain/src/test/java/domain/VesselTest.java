package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VesselTest {

    Vessel vessel = Vessel.with(VesselId.ofBoilKettle());

    @Test
    void hasSetpoint() {
        Temperature expectedTemperature = Temperature.inCelsius(62);
        vessel.setPoint(expectedTemperature);

        assertThat(vessel.setPoint()).isEqualTo(expectedTemperature);
    }

    @Test
    void toStringHasExpectedFields() {
        assertThat(vessel.toString()).isEqualTo("Vessel[id=VesselId[value=BoilKettle], setPoint=null]");
    }

    @Test
    void hasProfile() {
        TemperatureProfile expected = TemperatureProfile.create();
        vessel.profile(expected);

        assertThat(vessel.profile()).isEqualTo(expected);
    }
}