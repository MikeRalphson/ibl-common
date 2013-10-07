package bbc.iplayer.ibl.common.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bbc.iplayer.ibl.common.spring.configuration.CommonTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CommonTestConfiguration.class })
public class ConfigurationTest {

	@Value("${bbc.iplayer.ibl.common.aPropertyKey}")
	private String aProperty;

	@Test
	public void testAPropertyHasCorrectValue() throws Exception {
		assertNotNull(aProperty);
		assertEquals("aPropertyValue", aProperty);
	}
}