package application.port.input;

import application.port.output.VesselOutputPort;
import application.usecase.TemperatureProfileUseCase;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class TemperatureProfileUseCaseInputPortTest {
    TemperatureProfileUseCase useCase;

    VesselId vesselId;

    TemperatureProfile profile = TemperatureProfile.create();

    VesselOutputPort vesselOutputPort;

    @BeforeEach
    void setUp() {
        final Map<VesselId, Vessel> vessels = new HashMap<>();
        final Vessel mashTun = Vessel.with(VesselId.ofMashTun());
        vesselId = mashTun.id();
        vessels.put(mashTun.id(), mashTun);
        vesselOutputPort = new MyVesselOutputPort(vessels);
        useCase = TemperatureProfileUseCaseInputPort.with(vesselOutputPort);
    }

    @Test
    @DisplayName("saving a profile for a null VesselId should throw an exception")
    void saveProfile_nullVesselId() {
        assertThatIllegalArgumentException().isThrownBy(() -> useCase.saveProfile(null, profile))
                .withMessage("Saving a temperature profile requires a vessel id");
    }

    @Test
    @DisplayName("saving a profile that is null should throw an exception")
    void saveProfile_nullProfile() {
        assertThatIllegalArgumentException().isThrownBy(() -> useCase.saveProfile(vesselId, null))
                .withMessage("Temperature profile cannot be null");
    }

    @Test
    @DisplayName("I should be able to retrieve a profile after saving it to the vessel")
    void saveProfile_andLookup() {
        assertThat(vesselOutputPort.fetchVessel(vesselId)).isPresent();
        assertThat(vesselOutputPort.fetchVessel(vesselId).get().profile()).isNull();

        useCase.saveProfile(vesselId, profile);

        assertThat(vesselOutputPort.fetchVessel(vesselId).get().profile()).isEqualTo(profile);
    }

    private record MyVesselOutputPort(Map<VesselId, Vessel> vessels) implements VesselOutputPort {

        @Override
        public Optional<Vessel> fetchVessel(VesselId id) {
            return Optional.ofNullable(vessels.get(id));
        }

        @Override
        public void saveProfile(VesselId id, TemperatureProfile profile) {
            fetchVessel(id).ifPresent(vessel -> vessel.profile(profile));
        }

        @Override
        public void addMapping(VesselId id, int unitId) {

        }
    }

    @Test
    @DisplayName("Saving a profile over an existing profile should succeed silently")
    @Disabled
    void savingOverAnExistingProfileSucceedsSilently() {
        fail("Not implemented yet");
    }
}