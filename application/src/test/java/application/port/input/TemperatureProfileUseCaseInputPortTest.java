package application.port.input;

import application.port.output.VesselOutputPort;
import application.port.output.VesselOutputPortTestDouble;
import application.usecase.TemperatureProfileUseCase;
import domain.TemperatureControllerId;
import domain.TemperatureProfile;
import domain.Vessel;
import domain.VesselId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class TemperatureProfileUseCaseInputPortTest {
    TemperatureProfileUseCase useCase;

    VesselId vesselId;

    TemperatureProfile profile = TemperatureProfile.create();

    VesselOutputPort vesselOutputPort;

    @BeforeEach
    void setUp() {
        final Vessel mashTun = Vessel.with(VesselId.ofMashTun(), TemperatureControllerId.of(1));
        vesselId = mashTun.id();
        vesselOutputPort = new VesselOutputPortTestDouble();
        vesselOutputPort.addVessel(mashTun);
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
        assertThat(vesselOutputPort.findVessel(vesselId)).isPresent();
        assertThat(vesselOutputPort.findVessel(vesselId).get().profile()).isNull();

        useCase.saveProfile(vesselId, profile);

        assertThat(vesselOutputPort.findVessel(vesselId).get().profile()).isEqualTo(profile);
    }

    @Test
    @DisplayName("Saving a profile over an existing profile should succeed silently")
    @Disabled
    void savingOverAnExistingProfileSucceedsSilently() {
        fail("Not implemented yet");
    }
}