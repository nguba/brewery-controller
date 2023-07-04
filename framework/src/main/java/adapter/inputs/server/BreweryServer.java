package adapter.inputs.server;

import adapter.outputs.pxu.PxuNetwork;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication(scanBasePackages = "brewery")
public class BreweryServer {
    public static void main(String[] args) {
        SpringApplication.run(BreweryServer.class, args);
    }

    @Bean
    PxuNetwork redLionNetwork(SerialParameters parameters) throws Exception {
        return new PxuNetwork(parameters, Duration.ofSeconds(1)).start();
    }

    @Bean
    SerialParameters serialParameters() {
        SerialParameters parameters = new SerialParameters();
        parameters.setPortName("COM3");
        parameters.setEncoding("rtu");
        parameters.setBaudRate(9600);
        parameters.setParity("None");
        parameters.setDatabits(8);
        return parameters;
    }
}
