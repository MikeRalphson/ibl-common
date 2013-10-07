package bbc.iplayer.ibl.common.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = CommonConfiguration.class)
public class CommonTestConfiguration {

}
