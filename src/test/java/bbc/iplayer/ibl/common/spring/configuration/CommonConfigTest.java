package bbc.iplayer.ibl.common.spring.configuration;

import org.apache.camel.spring.spi.BridgePropertyPlaceholderConfigurer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@Configurable
@ContextConfiguration(classes = CommonTestConfiguration.class)
//@ContextConfiguration(locations = "classpath*:META-INF/spring/ibl-common-context.xml")
public class CommonConfigTest {

    @Autowired
    private BridgePropertyPlaceholderConfigurer bridgePropertyPlaceholder;

    @Value("${bbc.iplayer.ibl.http.max.num.connections}")
    private int httpMaxNumConnections;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        assertNotNull(bridgePropertyPlaceholder);
        assertThat(httpMaxNumConnections, is(equalTo(32)));
    }

}