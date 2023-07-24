package domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VesselRepositoryTest {

    VesselRepository repo = new VesselRepository();

    @Test
    void canAddAndRetrieveVessel() {
        repo.add(Vessel.asMashTun());

        assertThat(repo.read(VesselId.of("MashTun"))).isEqualTo(Vessel.asMashTun());
    }

}