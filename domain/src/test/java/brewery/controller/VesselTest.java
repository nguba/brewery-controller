package brewery.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VesselTest {

    @Test
    void isMashTun() {
        assertThat(Vessel.asMashTun().id()).isEqualTo(VesselId.of("MashTun"));
    }

    @Test
    void hasSetpoint() {
        Vessel vessel = Vessel.asMashTun();
        Temperature expectedTemperature = Temperature.inCelsius(62);
        vessel.setPoint(expectedTemperature);

        assertThat(vessel.setPoint()).isEqualTo(expectedTemperature);
    }

    @Test
    void toStringHasExpectedFields() {
        assertThat(Vessel.asMashTun().toString()).isEqualTo("Vessel[id=VesselId[value=MashTun], setPoint=null]");
    }
}