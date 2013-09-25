package bbc.iplayer.nitro.model.adapters;

import org.joda.time.format.ISODateTimeFormat;

public class IBLDateTimeAdapter extends DateTimeAdapter {

    public IBLDateTimeAdapter() {
        super(ISODateTimeFormat.dateTimeNoMillis());
    }
}
