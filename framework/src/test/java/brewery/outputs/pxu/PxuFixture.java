package brewery.outputs.pxu;

import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.time.Duration;

// TODO consider moving this into a Junit5 extension
public class PxuFixture {
    protected static RedLionPxuNetwork pxu;

    @BeforeAll
    static void init() throws Exception {
        final SerialParameters parameters = new SerialParameters();
        parameters.setPortName("COM3");
        parameters.setEncoding("rtu");
        parameters.setBaudRate(9600);
        parameters.setParity("None");
        parameters.setDatabits(8);

        pxu = new RedLionPxuNetwork(parameters, Duration.ofMillis(100)).start();
    }

    @AfterAll
    static void tearDown() throws Exception {
        pxu.stop();
    }
}
