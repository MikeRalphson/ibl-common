package bbc.iplayer.ibl.common.schema.adapters;

import org.joda.time.format.ISODateTimeFormat;

public class IBLDateTimeAdapter extends DateTimeAdapter {

    public IBLDateTimeAdapter() {
        super(ISODateTimeFormat.dateTimeNoMillis());
    }
}
