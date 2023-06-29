package brewery.inputs;

import brewery.outputs.pxu.RedLionPxuNetwork;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BreweryConfiguration {

    @Bean
    RedLionPxuNetwork redLionNetwork(SerialParameters parameters) throws Exception {
        return new RedLionPxuNetwork(parameters, Duration.ofSeconds(1)).start();
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
