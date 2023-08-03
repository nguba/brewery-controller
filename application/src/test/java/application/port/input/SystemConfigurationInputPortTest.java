package application.port.input;

import application.port.output.DataLoggerOutputPort;
import application.port.output.DataLoggerOutputPortAdapterInMemory;
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

    DataLoggerOutputPort dataLoggerOutputPort = new DataLoggerOutputPortAdapterInMemory();

    SystemConfigurationInputPort inputPort;

    Vessel vessel;

    @BeforeEach
    void setUp() {
        inputPort = SystemConfigurationInputPort.with(dataLoggerOutputPort);
        vessel = Vessel.with(vesselId, temperatureControllerId);
    }

    @Test
    @DisplayName("Verify that a controller cannot be added with a null vessel")
    void addControllerWithNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> inputPort.registerVessel(null))
                .withMessage("Vessel cannot be null");
    }

    @Test
    @DisplayName("Verify that a controller cannot be added with a null temperature controller")
    void addControllerWithNullTemperatureController() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> inputPort.registerVessel(Vessel.with(vesselId, null)))
                .withMessage("Vessel cannot have a null temperature controller");
    }

    @Test
    @DisplayName("Successfully adding a controller allows it to be retrieved by id")
    void addControllerIsPresent() {
        assertThat(dataLoggerOutputPort.findTemperatureControllerId(vesselId)).isEmpty();

        addTemperatureController();

        verifyTemperatureControllerIsRegistered();
    }

    private void verifyTemperatureControllerIsRegistered() {
        assertThat(dataLoggerOutputPort.findTemperatureControllerId(vesselId)).contains(temperatureControllerId);
    }

    @Test
    @DisplayName("Successfully adding a vessel allows the temperature controller id to be retrieved by vessel id")
    void addVesselWithControllerId() {
        assertThat(dataLoggerOutputPort.findTemperatureControllerId(vesselId)).isEmpty();

        addTemperatureController();

        verifyTemperatureControllerIsRegistered();
    }

    private void addTemperatureController() {
        inputPort.registerVessel(vessel);
    }

    @Test
    @DisplayName("Adding a vessel with a vessel id that already exists will throw an exception")
    void addVesselAlreadyRegistered() {
        addTemperatureController();
        assertThatIllegalArgumentException()
                .isThrownBy(() -> addTemperatureController())
                .withMessage("Vessel with VesselId[value=test] already exists, remove the vessel first");
    }

    @Test
    @DisplayName("Verify that a controller cannot be removed with a null vessel")
    void removeControllerWithNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> inputPort.removeVessel(null))
                .withMessage("Vessel cannot be null");
    }

    @Test
    @DisplayName("Remove succeeds silently even when the temperature controller id does not exist")
    void removeControllerWithNullTemperatureController() {
        addTemperatureController();
        assertThat(dataLoggerOutputPort.findTemperatureControllerId(vesselId)).isPresent();

        inputPort.removeVessel(Vessel.with(vesselId, null));

        assertThat(dataLoggerOutputPort.findTemperatureControllerId(vesselId)).isEmpty();
    }
}