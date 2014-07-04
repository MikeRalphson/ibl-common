package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

public class StringToASCII implements Function<String, List<Byte>> {
    @Override
    public List<Byte> apply(String input) {
        byte[] bytes = input.getBytes();
        return Arrays.asList(ArrayUtils.toObject(bytes));
    }
}
