package bbc.iplayer.ibl.common.spring.configuration;

import org.apache.camel.spring.spi.BridgePropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class PropertiesConfiguration {

	@Bean(name = "bridgePropertyPlaceholder")
	public BridgePropertyPlaceholderConfigurer bridgePropertyPlaceholder() {
		BridgePropertyPlaceholderConfigurer result = new BridgePropertyPlaceholderConfigurer();
		result.setIgnoreUnresolvablePlaceholders(true);
		result.setIgnoreResourceNotFound(true);
		result.setSystemPropertiesMode(BridgePropertyPlaceholderConfigurer.SYSTEM_PROPERTIES_MODE_OVERRIDE);
		result.setSearchSystemEnvironment(true);
		result.setLocations(new Resource[]{
								new ClassPathResource("classpath:config/environment.properties"),
								new ClassPathResource("classpath:config/environments/${SERVER_ENV}.properties")});
		return result;
	}
}
