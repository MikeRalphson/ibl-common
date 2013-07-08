package bbc.iplayer.spring.mashery.iodocs.model;

import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResourceMethod {

    private final List<Parameter> parameters;
    private final String name;
    private final String httpMethod;
    private final String description;
    private final String path;

    public ResourceMethod(
            String name, String httpMethod, String description,
            String path, List<Parameter> parameters) {
        this.name = name;
        this.httpMethod = httpMethod;
        this.description = description;
        this.path = path;
        this.parameters = parameters;
    }

    public LinkedHashMap<String, Object> getDataAsMaps() {
        LinkedHashMap<String, Object> method = Maps.newLinkedHashMap();

        method.put("httpMethod", httpMethod);
        method.put("path", path);

        if (!StringUtils.isEmpty(description))
            method.put("description", description);

        if (!parameters.isEmpty()) {
            Map<String, Object> parameterData = Maps.newLinkedHashMap();
            for (Parameter parameter : parameters) {
                parameterData.put(parameter.getName(), parameter.getDataAsMaps());
            }
            method.put("parameters", parameterData);
        }

        return method;
    }

    public String getName() {
        return name;
    }

}
