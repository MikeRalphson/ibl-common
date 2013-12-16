package uk.co.bbc.iplayer.common.utils;

import com.google.common.base.Preconditions;

import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

public class IOConversions {
    public static StreamSource toStreamSource(String s) {
        Preconditions.checkNotNull(s);
        return new StreamSource(new StringReader(s));
    }
}
