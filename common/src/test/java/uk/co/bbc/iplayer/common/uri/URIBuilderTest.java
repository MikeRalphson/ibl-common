package uk.co.bbc.iplayer.common.uri;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class URIBuilderTest {

    @Test
    public void parametersAreSorted() throws URISyntaxException {
        URIBuilder builder = new URIBuilder();
        builder.setHost("https://example.com");
        builder.setPath("test");
        builder.addParameter("charlie", "1");
        builder.addParameter("alpha", "2");
        builder.addParameter("bravo", "3");
        URI uri = builder.toURI();

        assertThat(uri.toString(), containsString("alpha=2&bravo=3&charlie=1"));
    }
}
