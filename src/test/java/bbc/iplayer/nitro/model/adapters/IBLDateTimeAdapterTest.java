package bbc.iplayer.nitro.model.adapters;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IBLDateTimeAdapterTest {

    private final static String SOURCE_DATE = "2013-04-16T10:32:00+0000";
    private final static String TARGET_DATE = "2013-04-16T10:32:00Z";

    @Test
    public void marshalToIBLDateTimeFormat() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd'T'HH:mm:ss+0000").withZone(DateTimeZone.UTC);
        DateTime dateTime = formatter.parseDateTime(SOURCE_DATE);

        IBLDateTimeAdapter adapter = new IBLDateTimeAdapter();

        assertThat(adapter.marshal(dateTime), is(TARGET_DATE));
    }

    @Test
    public void emptyDateStringReturnsNull() {
        IBLDateTimeAdapter adapter = new IBLDateTimeAdapter();
        DateTime dateTime = adapter.unmarshal("");
        assertNull(dateTime);
    }
}
