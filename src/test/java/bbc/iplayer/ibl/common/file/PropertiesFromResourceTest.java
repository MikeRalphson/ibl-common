package bbc.iplayer.ibl.common.file;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class PropertiesFromResourceTest {

    @Test
    public void propertiesLoaded() throws IOException {
        final ClassPathResource resource = mock(ClassPathResource.class);
        final InputStream inputStream = Mockito.spy(new ClassPathResource("platform-test.properties").getInputStream());
        when(resource.getInputStream()).thenReturn(inputStream);
        final Properties properties = PropertiesFromResource.getProperties(resource);
        assertThat(properties.getProperty("platform"), is("forge"));
        verify(inputStream).close();
    }
}
