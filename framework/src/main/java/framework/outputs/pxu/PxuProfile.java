package framework.outputs.pxu;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * The PXU profile is a series of 30 registers, each register is 16 bits.
 * Each tuple represents the target setpoint and the duration.
 * This allows for automated temperature profiles for mash, boil, and fermentation.
 */
public class PxuProfile {

    public PxuProfile(Register[] regs) {
        this.regs = regs;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PxuProfile.class.getSimpleName() + "[", "]")
                .add("regs=" + Arrays.toString(regs))
                .toString();
    }

    private final Register[] regs;

    public Segment segment(int index) {
        if (index > 14) throw new IllegalArgumentException("A segment index must be between 0 and 14.");
        int position = index * 2;
        return new Segment(regs[position].getValue(), regs[position + 1].getValue());
    }

    public record Segment(int setpoint, int duration) {
    }

//    public void printValues() {
//        int index = 0;
//        for (int i = 0; i < 30; i += 2) {
//            System.out.println("Seg[" + ++index + "] sP=" + regs[i].getValue() + " t=" + regs[i + 1].getValue());
//        }
//    }
}
