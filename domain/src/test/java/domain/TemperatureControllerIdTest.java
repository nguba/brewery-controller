package domain;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemperatureControllerIdTest {

    @Test
    @DisplayName("ValueObject observes equals and hashCode contract")
    public void equalsContract() {
        EqualsVerifier.forClass(TemperatureControllerId.class).verify();
    }

    @Test
    @DisplayName("toString contains expected information")
    public void toStringContainsValue() {
        assertThat(TemperatureControllerId.of(1).value()).isEqualTo(1);
    }
}