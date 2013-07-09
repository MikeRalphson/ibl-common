package bbc.iplayer.spring.mashery.iodocs;

import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsMethodName;
import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsResourceName;
import bbc.iplayer.spring.mashery.iodocs.dataextraction.AnnotatedMethodExtractor;
import bbc.iplayer.spring.mashery.iodocs.dataextraction.DescriptionExtractor;
import bbc.iplayer.spring.mashery.iodocs.dataextraction.ParamExtractor;
import bbc.iplayer.spring.mashery.iodocs.model.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Generates Mashery IO Docs JSON.
 */
public class SpringIoDocsGenerator {
    private final IoDocAPI ioDocAPI;

    private AnnotatedMethodExtractor annotatedMethodExtractor = new AnnotatedMethodExtractor();

    public SpringIoDocsGenerator(final IoDocAPI ioDocAPI) {
        this.ioDocAPI = ioDocAPI;
    }

    /**
     * Generates IO Docs JSON string
     *
     * @param endpoints           Classes with Spring request mappings annotations
     *                            The methods and params should be annotated with
     *                            the annotations in the annotations package. As
     *                            much info is taken from the RequestParam annotation
     *                            as possible.
     *                            For enums the enum descriptions are not mandatory but enums
     *                            are required if enum descriptions are specified.
     * @param extensionParameters parameters that are applicable to all endpoints
     * @return JSON as a string.
     */
    public String generateIoDocs(
            final Class<?>[] endpoints,
            final List<Parameter> extensionParameters) {
        LinkedHashMap<String, Object> json = Maps.newLinkedHashMap();
        json.put("name", ioDocAPI.getName());
        json.put("title", ioDocAPI.getTitle());
        json.put("description", ioDocAPI.getDescription());
        json.put("version", ioDocAPI.getVersion());
        json.put("basePath", ioDocAPI.getBasePath());
        json.put("protocol", ioDocAPI.getProtocol());
        json.put("auth", ioDocAPI.getAuth());

        Resources newResources = new Resources();
        for (Class<?> endpoint : endpoints) {
            getMethodsFromEndpoint(endpoint, extensionParameters, ioDocAPI.getEnvBasePath(), newResources);
        }
        json.put("resources", newResources.json());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    private <T> Resources getMethodsFromEndpoint(
            Class<T> endpointClass, List<Parameter> extensionParameters, final String envAppVersionPath, Resources result) {
        String endpointPath = "";
        final Class<RequestMapping> annotationClass = RequestMapping.class;

        endpointPath = getEndpointPath(endpointClass, endpointPath, annotationClass);

        List<Method> annotatedMethods = annotatedMethodExtractor.annotatedMethods(endpointClass);
        for (Method method : annotatedMethods) {
            String name = endpointClass.getSimpleName() + "_" + method.getName();
            RequestMethod httpMethod = null;
            String path = envAppVersionPath;
            String description = null;
            List<Parameter> parameters = Lists.newArrayList(extensionParameters);
            ResourceName resourceName = null;

            for (Annotation annotation : method.getAnnotations()) {
                final Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.equals(IoDocsResourceName.class)) {
                    final String value = ((IoDocsResourceName) annotation).value();
                    resourceName = new ResourceName(value);
                    result.addResource(resourceName);
                }
                if (annotationType.equals(IoDocsMethodName.class)) {
                    name = ((IoDocsMethodName) annotation).value();
                } else if (annotationType.equals(RequestMapping.class)) {
                    {
                        String restMethodPath = endpointPath;
                        final RequestMapping requestMapping = method.getAnnotation(annotationClass);
                        if (requestMapping != null) {
                            httpMethod = getRequestMethod(requestMapping);
                            restMethodPath = getRequestMappingPath(restMethodPath, requestMapping);
                        }

                        path += restMethodPath;
                        path = path.replace("{", ":").replace("}", "");
                    }

                    description = getFullDescription(method);

                    parameters.addAll(new ParamExtractor().getParamsFromMethod(method));
                }
            }

            if (resourceName == null) {
                throw new IllegalStateException("No resource specified");
            }


            if (httpMethod != null) {
                final ResourceMethod resourceMethod = new ResourceMethod(
                        name, httpMethod.name(), description, path, parameters);
                result.addMethod(resourceName, resourceMethod);
            }
        }
        return result;
    }

    private String getRequestMappingPath(String restMethodPath, RequestMapping requestMapping) {
        final String[] requestMappingPaths = requestMapping.value();
        if (requestMappingPaths.length > 1) {
            throw new UnsupportedOperationException("Can't yet support multiple request paths");
        }
        if (requestMappingPaths.length == 1) {
            restMethodPath += requestMappingPaths[0];
        }
        return restMethodPath;
    }

    private RequestMethod getRequestMethod(RequestMapping requestMapping) {
        RequestMethod httpMethod;
        final RequestMethod[] requestMethods = requestMapping.method();
        if (requestMethods.length > 1) {
            throw new UnsupportedOperationException("Can't yet support multiple request methods");
        }
        if (requestMethods.length == 0) {
            throw new UnsupportedOperationException("Need to specify request method");
        }
        httpMethod = requestMethods[0];
        return httpMethod;
    }

    private <T> String getEndpointPath(Class<T> endpointClass, String endpointPath, Class<RequestMapping> annotationClass) {
        if (endpointClass.getAnnotation(annotationClass) != null) {
            if (endpointClass.getAnnotation(annotationClass).value().length > 1) {
                throw new UnsupportedOperationException("Can't yet support multiple mappings");
            }
            endpointPath = endpointClass.getAnnotation(annotationClass).value()[0];
        }
        return endpointPath;
    }

    private String getFullDescription(Method method) {
        return new DescriptionExtractor().getDescription(method).or("");
    }
}
