/*
    Copyright (C) 2018  Nicolai P. Guba

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package brewery.controller;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemperatureTest
{
    @Test
    @DisplayName("Celsius to Celsius")
    void convertCelsiusToCelsius()
    {
        assertThat(Temperature.inCelsius(103.23).toCelsius()).isEqualTo(Temperature.inCelsius(103.23));
    }

    @Test
    @DisplayName("Celsius to Fahrenheit")
    void convertCelsiusToFahrenheit()
    {
        assertThat(Temperature.inCelsius(65.32).toFarenheit())
                .isEqualTo(Temperature.inFahrenheit(149.57));
    }

    @Test
    @DisplayName("Celsius to Kelvin")
    void convertCelsiusToKelvin()
    {
        assertThat(Temperature.inCelsius(103.23).toKelvin()).isEqualTo(Temperature.inKelvin(376.38));
    }

    @Test
    @DisplayName("Fahrenheit to Celsius")
    void convertFahrenheitToCelsius()
    {
        assertThat(Temperature.inFahrenheit(103.23).toCelsius()).isEqualTo(Temperature.inCelsius(39.57));
    }

    @Test
    @DisplayName("Fahrenheit to Farenheit")
    void convertFahrenheitToFahrenheit()
    {
        assertThat(Temperature.inFahrenheit(103.23).toFarenheit())
                .isEqualTo(Temperature.inFahrenheit(103.23));
    }

    @Test
    @DisplayName("Fahrenheit to Kelvin")
    void convertFarenheitToKelvin()
    {
        assertThat(Temperature.inFahrenheit(103.23).toKelvin()).isEqualTo(Temperature.inKelvin(312.72));
    }

    @Test
    @DisplayName("Kelvin to Celsius")
    void convertKelvinToCelsius()
    {
        assertThat(Temperature.inKelvin(103.23).toCelsius()).isEqualTo(Temperature.inCelsius(-169.92));
    }

    @Test
    @DisplayName("Kelvin to Fahrenheit")
    void convertKelvinToFahrenheit()
    {
        assertThat(Temperature.inKelvin(103.23).toFarenheit())
                .isEqualTo(Temperature.inFahrenheit(-273.86));
    }

    @Test
    @DisplayName("Kelvin to Kelvin")
    void convertKelvinToKelvin()
    {
        assertThat(Temperature.inKelvin(103.23).toKelvin()).isEqualTo(Temperature.inKelvin(103.23));
    }

    @Test
    @DisplayName("0 Celsius to Celsius")
    void convertZeroCelsiusToCelsius()
    {
        assertThat(Temperature.inCelsius(0).toCelsius()).isEqualTo(Temperature.inCelsius(0));
    }

    @Test
    @DisplayName("0 Celsius to Fahrenheit")
    void convertZeroCelsiusToFahrenheit()
    {
        assertThat(Temperature.inCelsius(0).toFarenheit()).isEqualTo(Temperature.inFahrenheit(32));
    }

    @Test
    @DisplayName("0 Fahrenheit to Celsius")
    void convertZeroFahrenheitToCelsius()
    {
        assertThat(Temperature.inFahrenheit(0).toCelsius()).isEqualTo(Temperature.inCelsius(-17.78));
    }

    @Test
    @DisplayName("0 Fahrenheit to Kelvin")
    void convertZeroFarenheitToKelvin()
    {
        assertThat(Temperature.inFahrenheit(0).toKelvin()).isEqualTo(Temperature.inKelvin(255.37));
    }

    @Test
    @DisplayName("0 Kelvin to Celsius")
    void convertZeroKelvinToCelsius()
    {
        assertThat(Temperature.inKelvin(0).toCelsius()).isEqualTo(Temperature.inCelsius(-273.15));
    }

    @Test
    @DisplayName("0 Kelvin to Fahrenheit")
    void convertZeroKelvinToFahrenheit()
    {
        assertThat(Temperature.inKelvin(0).toFarenheit()).isEqualTo(Temperature.inFahrenheit(-459.67));
    }

    @Test
    @DisplayName("0 Kelvin to Kelvin")
    void convertZeroKelvinToKelvin()
    {
        assertThat(Temperature.inKelvin(0).toKelvin()).isEqualTo(Temperature.inKelvin(0));
    }

    @Test
    void differenceInMagnitude()
    {
        assertThat(Temperature.inCelsius(10.0).difference(Temperature.inCelsius(4.0))).isEqualTo(6.0);
    }

    @Test
    void differenceInMagnitudeOtherIsGreater()
    {
        assertThat(Temperature.inCelsius(4.0).difference(Temperature.inCelsius(10.0))).isEqualTo(-6.0);
    }

    @Test
    void equalsContract()
    {
        EqualsVerifier.forClass(Temperature.class).verify();
    }

    // some tests are missing.  eg is x celsius above or below fahrenheit (maybe a new fixture for these?)
    @Test
    void isAbove()
    {
        assertThat(Temperature.inCelsius(34.1).isAbove(Temperature.inCelsius(34.0))).isTrue();
    }

    @Test
    void isBelow()
    {
        assertThat(Temperature.inCelsius(34.0).isBelow(Temperature.inCelsius(34.1))).isTrue();
    }

    @Test
    void isBelowOrAt()
    {
        assertThat(Temperature.inCelsius(34.0).isBelowOrAt(Temperature.inCelsius(34.0))).isTrue();
    }

    @Test
    void isNotAbove()
    {
        assertThat(Temperature.inCelsius(34.0).isAbove(Temperature.inCelsius(34.1))).isFalse();
    }

    @Test
    void isNotBelow()
    {
        assertThat(Temperature.inCelsius(34.1).isBelow(Temperature.inCelsius(34.0))).isFalse();
    }

    @Test
    void isNotBelowOrAt()
    {
        assertThat(Temperature.inCelsius(34.1).isBelowOrAt(Temperature.inCelsius(34.0))).isFalse();
    }

    @Test
    @DisplayName("Celsius string format")
    void toString_Celsius()
    {
        assertThat(Temperature.inCelsius(20.5).toString()).isEqualTo("20.5 (C)");
    }

    @Test
    @DisplayName("Fahrenheit string format")
    void toString_Fahrenheit()
    {
        assertThat(Temperature.inFahrenheit(12.3).toString()).isEqualTo("12.3 (F)");
    }

    @Test
    @DisplayName("Kelvin string format")
    void toString_Kelvin()
    {
        assertThat(Temperature.inKelvin(12.3).toString()).isEqualTo("12.3 (K)");
    }
}
