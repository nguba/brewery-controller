package brewery.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VesselTest {

    @Test
    void hasId() {
        VesselId expected = VesselId.of(1);
        Vessel vessel = Vessel.with(expected);

        assertEquals(expected, vessel.id());
    }

    @Test
    void hasSetpoint() {
        Vessel vessel = Vessel.with(VesselId.of(1));
        vessel.setPoint(Temperature.inCelsius(62));

        assertEquals(Temperature.inCelsius(62), vessel.setPoint());

        System.out.println(vessel);
    }
}