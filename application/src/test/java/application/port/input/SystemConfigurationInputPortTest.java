package application.port.input;

import application.port.output.VesselOutputPort;
import application.port.output.VesselOutputPortTestDouble;
import domain.TemperatureControllerId;
import domain.Vessel;
import domain.VesselId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class SystemConfigurationInputPortTest {
    VesselId vesselId = VesselId.of("test");

    TemperatureControllerId temperatureControllerId = TemperatureControllerId.of(6);

    VesselOutputPort vesselOutputPort = new VesselOutputPortTestDouble();

    SystemConfigurationInputPort inputPort;

    Vessel vessel;

    @BeforeEach
    void setUp() {
        inputPort = SystemConfigurationInputPort.with(vesselOutputPort);
        vessel = Vessel.with(vesselId, temperatureControllerId);
    }

    @Test
    @DisplayName("Verify that a vessel cannot be added with a null vessel")
    void addVesselWithNullVessel() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> inputPort.addVessel(null))
                .withMessage("Vessel cannot be null");
    }

    @Test
    @DisplayName("Verify that a vessel cannot be added with a null temperature controller")
    void addVesselWithNullTemperatureController() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> inputPort.addVessel(Vessel.with(vesselId, null)))
                .withMessage("Vessel cannot have a null temperature controller");
    }

    @Test
    @DisplayName("Successfully adding a vessel allows it to be retrieved by id")
    void addVesselIsPresent() {
        assertThat(vesselOutputPort.findVessel(vesselId)).isEmpty();

        addVessel();

        assertThat(vesselOutputPort.findVessel(vesselId)).isPresent();
    }

    @Test
    @DisplayName("Successfully adding a vessel allows the temperature controller id to be retrieved by vessel id")
    void addVesselWithControllerId() {
        assertThat(vesselOutputPort.findVessel(vesselId)).isEmpty();

        addVessel();

        assertThat(vesselOutputPort.findTemperatureControllerId(vesselId)).isEqualTo(temperatureControllerId);
    }

    private void addVessel() {
        inputPort.addVessel(vessel);
    }

    @Test
    @DisplayName("Adding a vessel with a vessel id that already exists will throw an exception")
    void addVesselAlreadyRegistered() {
        addVessel();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> addVessel())
                .withMessage("Vessel with VesselId[value=test] already exists, remove the vessel first");
    }
}