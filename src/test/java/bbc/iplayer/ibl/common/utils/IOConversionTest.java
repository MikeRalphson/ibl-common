package bbc.iplayer.ibl.common.utils;

import org.junit.Test;

import javax.xml.transform.stream.StreamSource;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class IOConversionTest {

    private final static String SOURCE_STR = "someString";

    @Test
    public void toSourceStream() {
        assertThat(IOConversions.toStreamSource(SOURCE_STR),
                instanceOf(StreamSource.class));
    }

    @Test(expected = NullPointerException.class)
    public void nullStringGuard() {
        IOConversions.toStreamSource(null);
    }
}
