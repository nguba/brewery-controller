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

    @BeforeEach
    void setUp() {
        inputPort = SystemConfigurationInputPort.with(vesselOutputPort);
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
    void addVessel() {
        assertThat(vesselOutputPort.findVessel(vesselId)).isEmpty();

        inputPort.addVessel(Vessel.with(vesselId, temperatureControllerId));

        assertThat(vesselOutputPort.findVessel(vesselId)).isPresent();
    }

    @Test
    @DisplayName("Successfully adding a vessel allows the temperature controller id to be retrieved by vessel id")
    void addVesselWithControllerId() {
        assertThat(vesselOutputPort.findVessel(vesselId)).isEmpty();

        inputPort.addVessel(Vessel.with(vesselId, temperatureControllerId));

        assertThat(vesselOutputPort.findTemperatureControllerId(vesselId)).isEqualTo(temperatureControllerId);
    }
}