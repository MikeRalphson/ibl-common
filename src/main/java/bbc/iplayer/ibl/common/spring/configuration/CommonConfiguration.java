package bbc.iplayer.ibl.common.spring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"bbc.iplayer.ibl.common"})
@Import({PropertiesConfiguration.class})
public class CommonConfiguration {

}
