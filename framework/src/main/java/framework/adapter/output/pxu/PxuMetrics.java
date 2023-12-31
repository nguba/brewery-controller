package framework.adapter.output.pxu;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.time.Duration;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * <p>
 * This class allows for conveniently reading the registers of the RedLion PXU PID controller
 * to obtain the most frequently used metrics.
 * </p>
 * <p>
 * For a description of each state, see the utility methods returning the value, or the
 * PXU documentation at page 48.
 * </p>
 *
 * @see "framework/doc/PXU Manual.pdf"
 */
public class PxuMetrics {

    public enum Status {
        STOP, RUN, END, PAUSE, ADVANCE
    }

    private final Register[] registers;

    public PxuMetrics(Register[] registers) {
        this.registers = registers;
    }

    public double processValue() {
        return registers[0].getValue() / 10.0;
    }

    public double activeSetpoint() {
        return registers[1].getValue() / 10.0;
    }

    public String status() {

        switch (registers[17].getValue()) {
            case 0 -> {
                return Status.STOP.name();
            }
            case 1 -> {
                return Status.RUN.name();
            }
            case 2 -> {
                return Status.END.name();
            }
            case 3 -> {
                return Status.PAUSE.name();
            }
            case 4 -> {
                return Status.ADVANCE.name();
            }
        }
        return "UNKNOWN";
    }

    public double outputOne() {
        return registers[8].getValue() / 10.0;
    }

    public double outputTwo() {
        return registers[9].getValue() / 10.0;
    }

    public String pidParameterSet() {
        switch (registers[14].getValue()) {
            case 0 -> {
                return "PID1";
            }
            case 1 -> {
                return "PID2";
            }
            case 2 -> {
                return "PID3";
            }
            case 3 -> {
                return "PID4";
            }
            case 4 -> {
                return "PID5";
            }
            case 5 -> {
                return "PID6";
            }
            case 6 -> {
                return "AUTO";
            }
        }
        return "UNKNOWN";
    }

    public int currentProfile() {
        return registers[25].getValue();
    }


    public int currentProfileSegment() {
        return registers[26].getValue();
    }


    public Duration profileSegmentRemainingTime() {
        float remainingTimeInDecimal = registers[27].getValue() / 10.0f;
        return Duration.ofSeconds((long) (remainingTimeInDecimal * 60));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PxuMetrics.class.getSimpleName() + "[", "]")
                .add("registers=" + Arrays.toString(registers))
                .toString();
    }
}
