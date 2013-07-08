package bbc.iplayer.spring.mashery.iodocs.dataextraction;

import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsIgnore;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;

public class AnnotatedMethodExtractor {

    public List<Method> annotatedMethods(final Class<?> endpointClass) {
        final Method[] methods = endpointClass.getMethods();
        List<Method> result = Lists.newArrayList();
        for (Method method : methods) {
            if (method.isAnnotationPresent(IoDocsIgnore.class)) continue;
            if (method.isAnnotationPresent(RequestMapping.class)) {
                result.add(method);
                continue;
            }
        }
        return result;
    }
}
