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
package domain;

import java.util.Objects;

/**
 *
 * @author <a href="mailto:nguba@mac.com">Nico Guba</a>
 */
public final class Temperature
{
    public enum Scale
    {
        CELSIUS("C"), FAHRENHEIT("F"), KELVIN("K");

        private final String symbol;

        Scale(final String symbol)
        {
            this.symbol = symbol;
        }

        @Override
        public String toString()
        {
            return symbol;
        }
    }

    public static Temperature inCelsius(final double value)
    {
        return new Temperature(value, Scale.CELSIUS);
    }

    public static Temperature inFahrenheit(final double value)
    {
        return new Temperature(value, Scale.FAHRENHEIT);
    }

    public static Temperature inKelvin(final double value)
    {
        return new Temperature(value, Scale.KELVIN);
    }

    private static double truncate(final double c)
    {
        return Math.floor(c * 100) / 100;
    }

    private final Scale scale;

    private final double value;

    private Temperature(final double value, final Scale scale)
    {
        this.value = value;
        this.scale = scale;
    }

    /**
     * Returns the difference in magnitude between this and another temperature.
     *
     * @param other
     *            the temperature to compare with
     * @return the resulting magnitude. Negative if the other value is greater.
     */
    public double difference(final Temperature other)
    {
        return value - other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Double.compare(that.value, value) == 0 && scale == that.scale;
    }

    @Override
    public int hashCode() {
        return Objects.hash(scale, value);
    }

    public boolean isAbove(final Temperature temperature)
    {
        return value > temperature.value;
    }

    public boolean isBelow(final Temperature temperature)
    {
        return value < temperature.value;
    }

    public boolean isBelowOrAt(final Temperature temperature)
    {
        return value <= temperature.value;
    }

    public Temperature toCelsius()
    {
        switch (scale) {
            case FAHRENHEIT -> {
                final double c = (value - 32) * 0.5556;
                return Temperature.inCelsius(truncate(c));
            }
            case KELVIN -> {
                final double c = value - 273.15;
                return Temperature.inCelsius(truncate(c));
            }
            default -> {
                return this;
            }
        }
    }

    public Temperature toFarenheit()
    {
        switch (scale) {
            case CELSIUS -> {
                final double f = 9.0 / 5.0 * value + 32;
                return Temperature.inFahrenheit(truncate(f));
            }
            case KELVIN -> {
                final double f = (value - 273.15) * 9.0 / 5.0 + 32;
                return Temperature.inFahrenheit(truncate(f));
            }
            default -> {
                return this;
            }
        }
    }

    public Temperature toKelvin()
    {
        switch (scale) {
            case CELSIUS -> {
                final double c = value + 273.15;
                return Temperature.inKelvin(truncate(c));
            }
            case FAHRENHEIT -> {
                final double k = (value + 459.67) * (5.0 / 9.0);
                return Temperature.inKelvin(truncate(k));
            }
            default -> {
                return this;
            }
        }
    }

    @Override
    public String toString()
    {
        return value + " (" + scale + ")";
    }
}
