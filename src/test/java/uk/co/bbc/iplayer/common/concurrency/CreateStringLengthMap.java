package uk.co.bbc.iplayer.common.concurrency;

import com.google.common.base.Function;

import java.util.HashMap;
import java.util.Map;

public class CreateStringLengthMap implements Function<String, Map<String, Integer>> {
    @Override
    public Map<String, Integer> apply(String input) {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put(input, input.length());
        return m;
    }
}
