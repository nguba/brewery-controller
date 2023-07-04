package adapter.outputs.pxu;

import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.junit.jupiter.api.extension.*;

import java.time.Duration;

public class PxuNetworkExtension implements ParameterResolver, BeforeAllCallback, AfterAllCallback {
    private final PxuNetwork pxuNetwork;

    public PxuNetworkExtension() {
        final SerialParameters parameters = new SerialParameters();
        parameters.setPortName("COM3");
        parameters.setEncoding("rtu");
        parameters.setBaudRate(9600);
        parameters.setParity("None");
        parameters.setDatabits(8);

        this.pxuNetwork = new PxuNetwork(parameters, Duration.ofMillis(100));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(PxuNetwork.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        try {
            return pxuNetwork;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        pxuNetwork.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        pxuNetwork.start();
    }
}
