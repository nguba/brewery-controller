package adapter.outputs.pxu;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.Arrays;

public class PxuProfile {

    public PxuProfile(int unitId, Register[] regs) {
        this.unitId = unitId;
        this.regs = regs;
    }

    private final int unitId;
    private final Register[] regs;

    public int unitId() {
        return unitId;
    }

    @Override
    public String toString() {
        return "PxuProfile{" +
                "unitId=" + unitId +
                ", regs=" + Arrays.toString(regs) +
                '}';
    }

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
