package bbc.iplayer.ibl.common.spring.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <p>
 * 	A Spring @Configuration class to configure the beans of the ibl-common module.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see org.springframework.context.annotation.Configuration
 * @see bbc.iplayer.ibl.common.spring.configuration.PropertiesConfiguration
 */
@Configuration
@ComponentScan(basePackages = {"bbc.iplayer.ibl.common"})
@Import({PropertiesConfiguration.class})
public class CommonConfiguration {

}
