package bbc.iplayer.spring.mashery.iodocs.model;

import com.google.common.collect.Maps;

import java.util.LinkedHashMap;
import java.util.Map;


public class Resources {

    private LinkedHashMap<ResourceName, LinkedHashMap<String, Object>> resources;

    public Resources() {
        resources = new LinkedHashMap<ResourceName, LinkedHashMap<String, Object>>();
    }

    @Override
    public String toString() {
        return resources.toString();
    }

    public LinkedHashMap<ResourceName, LinkedHashMap<String, Object>> json() {
        return resources;
    }

    public void addResource(ResourceName name) {
        if (!resources.containsKey(name)) {
            LinkedHashMap<String, Object> methodsMap = new LinkedHashMap<String, Object>();
            methodsMap.put("methods", Maps.<String, Object>newLinkedHashMap());
            resources.put(name, methodsMap);
        }
    }

    public void addMethod(ResourceName name, ResourceMethod method) {
        if (!resources.containsKey(name)) {
            addResource(name);
        }
        ((Map<String, Object>) resources.get(name).get("methods")).put(method.getName(), method.getDataAsMaps());
    }
}
