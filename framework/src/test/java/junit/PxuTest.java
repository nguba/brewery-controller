package junit;

import framework.inputs.server.BreweryServer;
import junit.extension.PxuNetworkExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootTest(classes = {BreweryServer.class})
@ExtendWith({PxuNetworkExtension.class, SpringExtension.class})
public @interface PxuTest {
}
