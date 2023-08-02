package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VesselIdTest {

    @Test
    @DisplayName("ofHotLiquorTun() should return a VesselId with the value 'HotLiquorTun'")
    void ofHotLiquorTun() {
        assertThat(VesselId.ofHotLiquorTun()).isEqualTo(VesselId.of("HotLiquorTun"));
    }

    @Test
    @DisplayName("ofBoilKettle() should return a VesselId with the value 'BoilKettle'")
    void ofBoilKettle() {
        assertThat(VesselId.ofBoilKettle()).isEqualTo(VesselId.of("BoilKettle"));
    }

    @Test
    @DisplayName("ofFermenter() should return a VesselId with the value 'Fermenter'")
    void ofFermenter() {
        assertThat(VesselId.ofFermenter()).isEqualTo(VesselId.of("Fermenter"));
    }

    @Test
    @DisplayName("ofConditioningTank() should return a VesselId with the value 'ConditioningTank'")
    void ofConditioningTank() {
        assertThat(VesselId.ofConditioningTank()).isEqualTo(VesselId.of("ConditioningTank"));
    }

    @Test
    @DisplayName("ofMashTun() should return a VesselId with the value 'MashTun'")
    void ofMashTun() {
        assertThat(VesselId.ofMashTun()).isEqualTo(VesselId.of("MashTun"));
    }

    @Test
    @DisplayName("value() should return the value of the VesselId")
    void value() {
        assertThat(VesselId.of("test").value()).isEqualTo("test");
    }
}