package bbc.iplayer.ibl.common.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * <p>
 * 	A Spring @Configuration class to configure the PropertyPlaceholderConfigurer of the ibl project. *  
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
 * @see org.apache.camel.spring.spi.BridgePropertyPlaceholderConfigurer
 *
 */
@Configuration
@ImportResource(value = "classpath*:META-INF/spring/ibl-common-context.xml")
public class PropertiesConfiguration {

}
