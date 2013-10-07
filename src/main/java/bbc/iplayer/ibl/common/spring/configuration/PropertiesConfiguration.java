package bbc.iplayer.ibl.common.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = "classpath*:META-INF/spring/ibl-common-context.xml") 
public class PropertiesConfiguration {

}
