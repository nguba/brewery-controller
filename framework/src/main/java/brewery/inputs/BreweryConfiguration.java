package brewery.inputs;

import brewery.outputs.pxu.PxuNetwork;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BreweryConfiguration {

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
