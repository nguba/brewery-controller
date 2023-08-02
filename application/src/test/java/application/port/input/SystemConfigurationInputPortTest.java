package application.port.input;

import application.port.output.TemperatureControllerOutputPort;
import application.port.output.TemperatureControllerOutputPortAdapterInMemory;
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

    TemperatureControllerOutputPort temperatureControllerOutputPort = new TemperatureControllerOutputPortAdapterInMemory();

    SystemConfigurationInputPort inputPort;

    Vessel vessel;

    @BeforeEach
    void setUp() {
        inputPort = SystemConfigurationInputPort.with(temperatureControllerOutputPort);
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
        assertThat(temperatureControllerOutputPort.findTemperatureControllerId(vesselId)).isEmpty();

        addTemperatureController();

        assertThat(temperatureControllerOutputPort.findTemperatureControllerId(vesselId)).contains(temperatureControllerId);
    }

    @Test
    @DisplayName("Successfully adding a vessel allows the temperature controller id to be retrieved by vessel id")
    void addVesselWithControllerId() {
        assertThat(temperatureControllerOutputPort.findTemperatureControllerId(vesselId)).isEmpty();

        addTemperatureController();

        assertThat(temperatureControllerOutputPort.findTemperatureControllerId(vesselId)).contains(temperatureControllerId);
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
}