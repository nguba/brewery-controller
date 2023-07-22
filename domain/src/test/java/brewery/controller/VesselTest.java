package brewery.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VesselTest {

    @Test
    void isMashTun() {
        assertThat(Vessel.asMashTun().id()).isEqualTo(VesselId.of("MashTun"));
    }

    @Test
    void isHotLiquorTun() {
        assertThat(Vessel.asHotLiquorTun().id()).isEqualTo(VesselId.of("HotLiquorTun"));
    }

    @Test
    void isBoilKettle() {
        assertThat(Vessel.asBoilKettle().id()).isEqualTo(VesselId.of("BoilKettle"));
    }

    @Test
    void isFermenter() {
        assertThat(Vessel.asFermenter().id()).isEqualTo(VesselId.of("Fermenter"));
    }

    @Test
    void isConditioningTank() {
        assertThat(Vessel.asConditioningTank().id()).isEqualTo(VesselId.of("ConditioningTank"));
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