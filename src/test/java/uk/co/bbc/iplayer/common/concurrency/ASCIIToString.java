package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;

import java.util.List;

public class ASCIIToString implements Function<List<Byte>, String> {
    @Override
    public String apply(List<Byte> input) {
        StringBuffer sb = new StringBuffer();
        for (Byte b : input) {
            char c = (char) b.byteValue();
            sb.append(c);
        }
        return sb.toString();
    }
}
