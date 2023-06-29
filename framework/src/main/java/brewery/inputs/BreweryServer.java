package brewery.inputs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = "brewery")
public class BreweryServer {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BreweryServer.class, args);

        //context.getBean(PxuManager.class).start();
    }
}
