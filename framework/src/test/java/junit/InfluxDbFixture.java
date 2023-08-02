package junit;

import junit.extension.InfluxVersion;
import junit.extension.InfluxdbClientExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({InfluxdbClientExtension.class})
@InfluxVersion("2.7.1")
public @interface InfluxDbFixture {
}
