package junit.extension;

import adapter.outputs.pxu.PxuNetwork;
import org.junit.jupiter.api.extension.*;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PxuNetworkExtension implements ParameterResolver, BeforeAllCallback, AfterAllCallback {
    private PxuNetwork pxuNetwork;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(PxuNetwork.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return pxuNetwork;
    }

    @Override
    public void afterAll(ExtensionContext context) {
        pxuNetwork.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        pxuNetwork = applicationContext.getBean(PxuNetwork.class);
        pxuNetwork.start();
    }
}
