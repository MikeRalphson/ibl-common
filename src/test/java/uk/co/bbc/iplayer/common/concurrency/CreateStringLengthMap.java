package uk.co.bbc.iplayer.common.concurrency;

import uk.co.bbc.iplayer.common.functions.ThrowableFunction;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spragn01 on 26/03/2014.
 */
public class CreateStringLengthMap implements ThrowableFunction<String, Map<String, Integer>> {
    @Override
    public Map<String, Integer> apply(String input) throws Exception {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put(input, input.length());
        return m;
    }
}
