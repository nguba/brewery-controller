package junit.extension;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

// Close the DB connection after the test
public class InfluxdbClientExtension implements BeforeAllCallback, AfterAllCallback, ParameterResolver {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(InfluxdbClientExtension.class);

    private InfluxDBContainer<?> container;

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(InfluxDBClient.class) || parameterContext.getParameter().getType().equals(InfluxDBContainer.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        LOGGER.info("Resolving: {}", parameterContext.getParameter().getType().getSimpleName());

        return switch (parameterContext.getParameter().getType().getSimpleName()) {
            case "InfluxDBClient" -> resolveInfluxDBClient();
            case "InfluxDBContainer" -> container;
            default ->
                    throw new ParameterResolutionException("Unknown parameter type: " + parameterContext.getParameter().getType().getSimpleName());
        };
    }

    @NotNull
    private InfluxDBClient resolveInfluxDBClient() {
        InfluxDBClientOptions.Builder builder = InfluxDBClientOptions.builder();
        builder.url(container.getUrl());
        builder.org(container.getOrganization());
        builder.bucket(container.getBucket());
        builder.authenticate(container.getUsername(), container.getPassword().toCharArray());
        builder.org(container.getOrganization());
        return InfluxDBClientFactory.create(builder.build());
    }


    @Override
    public void beforeAll(ExtensionContext context) {
        AtomicReference<String> influxDbVersion = new AtomicReference<>("2.7.0");
        Optional<InfluxVersion> influxVersion =
                Optional.ofNullable(context.getRequiredTestClass().getAnnotation(InfluxVersion.class));
        influxVersion.ifPresent(version -> {
            influxDbVersion.set(version.value());
            LOGGER.info("Configuring test for InfluxDB version: " + influxDbVersion);
        });

        container = new InfluxDBContainer<>(DockerImageName.parse("influxdb:" + influxDbVersion));
        if (!container.isRunning()) {
            LOGGER.info("Starting InfluxDB container");
            container.start();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (container.isRunning()) {
            LOGGER.info("Stopping InfluxDB container");
            container.stop();
        }
    }
}
