package uk.co.bbc.iplayer.common.uri;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URIBuilder {

    private final static Pattern PROTOCOL = Pattern.compile("((?:\\w+))://(.*)");
    private String protocol = "http";
    private String host = "";
    private String path = "";
    private SortedMap<String, List<String>> parameters = new TreeMap<String, List<String>>();

    public URIBuilder setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public URIBuilder setHost(String host) {
        // Does host already have a protocol?
        Matcher m = PROTOCOL.matcher(host);
        if (m.matches()) {
            setProtocol(m.group(1));
            setHost(m.group(2));
        } else {
            this.host = host;
        }

        return this;
    }

    public URIBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Add a parameter. Doesn't remove previously set parameters. You can have
     * multiple parameters with the same key.
     */
    public URIBuilder addParameter(String key, String value) {
        if (this.parameters.containsKey(key)) {
            this.parameters.get(key).add(value);
        } else {
            ArrayList<String> values = new ArrayList<String>();
            values.add(value);
            this.parameters.put(key, values);
        }
        return this;
    }

    public URI toURI() throws URISyntaxException {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol);
        sb.append("://");
        sb.append(host);

        if (!path.startsWith("/")) {
            sb.append("/");
        }
        sb.append(path);
        if (!parameters.isEmpty()) {
            sb.append("?");
            for (Map.Entry<String, List<String>> parameter : parameters.entrySet()) {
                for (String value : parameter.getValue()) {
                    sb.append(parameter.getKey());
                    sb.append("=");
                    sb.append(value);
                    sb.append("&");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return new URI(sb.toString());
    }

    public String getHost() {
        return host;
    }
}