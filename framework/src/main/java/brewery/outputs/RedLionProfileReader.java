package brewery.outputs;

import com.ghgande.j2mod.modbus.procimg.Register;

import java.util.Arrays;

public class RedLionProfileReader {

    public RedLionProfileReader(int unitId, Register[] regs) {
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
        return "RedLionProfileReader{" +
                "unitId=" + unitId +
                ", regs=" + Arrays.toString(regs) +
                '}';
    }

    public void printValues() {
        int index = 0;
        for(int i = 0; i < 30; i+= 2) {
            System.out.println("Seg[" + ++index + "] sP=" + regs[i].getValue() + " t=" + regs[i+1].getValue());
        }
    }
}
