package uk.co.bbc.iplayer.common.uri;

import com.google.common.collect.Maps;

import java.util.Map;

public class FilterCapturer implements FilterBuilder {

    public Map<String, String> captured() {
        return captured;
    }

    private Map<String, String> captured = Maps.newHashMap();

    @Override
    public FilterBuilder addFilter(String filter, String value) {
        captured.put(filter, value);
        return this;
    }
}
