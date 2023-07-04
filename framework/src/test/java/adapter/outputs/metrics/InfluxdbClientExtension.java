package adapter.outputs.metrics;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.utility.DockerImageName;

public class InfluxdbClientExtension implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(InfluxDBClient.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        InfluxDBContainer<?> container = new InfluxDBContainer<>(
                DockerImageName.parse("influxdb:2.7.1")
        );
        container.start();
        InfluxDBClientOptions.Builder builder = InfluxDBClientOptions.builder();
        builder.url(container.getUrl());
        builder.org(container.getOrganization());
        builder.bucket(container.getBucket());//.authenticateToken(container.getToken())
        InfluxDBClientOptions options = builder.build();

        return InfluxDBClientFactory.create(options);
    }
}
