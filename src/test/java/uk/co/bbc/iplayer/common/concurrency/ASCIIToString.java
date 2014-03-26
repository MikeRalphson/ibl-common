package uk.co.bbc.iplayer.common.concurrency;

import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.List;

/**
 * Created by spragn01 on 26/03/2014.
 */
public class ASCIIToString implements ThrowableFunction<List<Byte>, String> {
    @Override
    public String apply(List<Byte> input) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (Byte b : input) {
            char c = (char) b.byteValue();
            sb.append(c);
        }
        return sb.toString();
    }
}
