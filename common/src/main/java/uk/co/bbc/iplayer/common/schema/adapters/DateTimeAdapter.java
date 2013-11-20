package uk.co.bbc.iplayer.common.schema.adapters;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

    private DateTimeFormatter formatter;

    public DateTimeAdapter(String format) {
        this.formatter = DateTimeFormat.forPattern(format).withZone(DateTimeZone.UTC);
    }

    public DateTimeAdapter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public DateTime unmarshal(String s) {

        if (!StringUtils.isBlank(s)) {
            try {
                return formatter.parseDateTime(s);
            } catch (IllegalArgumentException e) {
                throw new InvalidDateFormatException(e);
            }
        }

        return null;
    }

    @Override
    public String marshal(DateTime dt) {
        return dt == null ? null : formatter.print(dt);
    }

}