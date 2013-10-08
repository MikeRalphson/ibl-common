package bbc.iplayer.common.utils;

import org.apache.commons.lang.builder.ToStringStyle;

public class NewLineStyle extends ToStringStyle {

    private static final String NEWLINE = "\n";

    public void appendStart(StringBuffer buffer, Object object) {
        super.appendStart(buffer, object);
        buffer.append(NEWLINE);
    }

    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        buffer.append(value);
        buffer.append(NEWLINE);
    }
}
