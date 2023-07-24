package framework.inputs.server;

import framework.outputs.pxu.EventPublisher;
import framework.outputs.pxu.PxuNetwork;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import com.google.common.eventbus.EventBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class BreweryServer {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BreweryServer.class, args).getBean(PxuNetwork.class).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    PxuNetwork pxuNetwork(ModbusSerialMaster master, EventPublisher publisher) {
        return new PxuNetwork(master, Duration.ofSeconds(1), publisher);
    }

    @Bean
    EventPublisher eventPublisher(EventBus eventBus) {
        return eventBus::post;
    }

    @Bean
    EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    ModbusSerialMaster master(SerialParameters parameters) {
        return new ModbusSerialMaster(parameters, 200);
    }

    @Bean
    SerialParameters serialParameters() {
        final SerialParameters parameters = new SerialParameters();
        parameters.setPortName("COM3");
        parameters.setEncoding("rtu");
        parameters.setBaudRate(9600);
        parameters.setParity("None");
        parameters.setDatabits(8);
        return parameters;
    }
}
