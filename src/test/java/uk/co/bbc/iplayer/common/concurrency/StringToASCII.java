package uk.co.bbc.iplayer.common.concurrency;

import org.apache.commons.lang3.ArrayUtils;
import uk.co.bbc.iplayer.common.functions.ThrowableFunction;

import java.util.Arrays;
import java.util.List;

/**
 * Created by spragn01 on 26/03/2014.
 */
public class StringToASCII implements ThrowableFunction<String, List<Byte>> {
    @Override
    public List<Byte> apply(String input) throws Exception {
        byte[] bytes = input.getBytes();
        return Arrays.asList(ArrayUtils.toObject(bytes));
    }
}
