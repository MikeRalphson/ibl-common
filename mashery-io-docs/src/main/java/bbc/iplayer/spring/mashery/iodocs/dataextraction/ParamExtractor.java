package bbc.iplayer.spring.mashery.iodocs.dataextraction;


import bbc.iplayer.spring.mashery.iodocs.annotations.*;
import bbc.iplayer.spring.mashery.iodocs.model.Parameter;
import bbc.iplayer.spring.mashery.iodocs.model.TypedParameter;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParamExtractor {


    public List<Parameter> getParamsFromMethod(final Method method) {
        final ArrayList<Parameter> result = Lists.newArrayList();
        for (int paramIndex = 0; paramIndex < method.getParameterTypes().length; paramIndex++) {
            Annotation[] parameterAnnotations = method.getParameterAnnotations()[paramIndex];

            if (paramIsIgnored(parameterAnnotations)) continue;

            Parameter.Type paramType = getType(method.getParameterTypes()[paramIndex]);

            Object defaultValue = getDefault(parameterAnnotations);
            final Optional<LocationDetails> optionalLocationDetails = getLocation(parameterAnnotations);
            if (!optionalLocationDetails.isPresent()) {
                continue;
            }
            LocationDetails locationDetails = optionalLocationDetails.get();
            Parameter.Location location = locationDetails.location;
            String name = locationDetails.name;
            boolean paramRequired = locationDetails.isRequired;

            String paramDesc = new DescriptionExtractor().getDescription(parameterAnnotations).or("");
            List<String> enums = getEnumsToOneLevelOfNesting(parameterAnnotations);
            List<String> enumDescs = getEnumDescriptionsToOneLevelOfNesting(parameterAnnotations);

            if (enumDescs.size() > 0 & (enums.size() != enumDescs.size())) {
                throw new IllegalStateException("Number of enums and descriptions not matching for " + name);
            }
            paramType = handleBoolean(paramType, enums, enumDescs);
            result.add(new Parameter(name, paramDesc, location, paramType, paramRequired, defaultValue, enums, enumDescs));

        }
        return result;
    }

    private Parameter.Type handleBoolean(Parameter.Type paramType, List<String> enums, List<String> enumDescs) {
        if (paramType == Parameter.Type.BOOLEAN) {
            if (enums.size() == 0) {
                enums.add("true");
                enums.add("false");
                enumDescs.add("true");
                enumDescs.add("false");
            }
            paramType = Parameter.Type.STRING;
        }
        return paramType;
    }

    private Optional<LocationDetails> getLocation(final Annotation[] parameterAnnotations) {
        for (int i = 0; i < parameterAnnotations.length; i++) {

            final Annotation annotation = parameterAnnotations[i];
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(PathVariable.class)) {
                return Optional.of(new LocationDetails(((PathVariable) annotation).value(), Parameter.Location.pathReplace, true));
            } else if (annotationType.equals(RequestParam.class)) {
                final RequestParam requestParam = (RequestParam) annotation;
                return Optional.of(new LocationDetails(requestParam.value(), Parameter.Location.query, requestParam.required()));
            }
        }
        return Optional.absent();
    }

    private List<String> getEnumsToOneLevelOfNesting(final Annotation[] parameterAnnotations) {
        for (int i = 0; i < parameterAnnotations.length; i++) {

            final Annotation annotation = parameterAnnotations[i];
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(IoDocsEnum.class)) {
                return Lists.newArrayList(((IoDocsEnum) annotation).value());
            }

        }
        for (int i = 0; i < parameterAnnotations.length; i++) {

            final Annotation annotation = parameterAnnotations[i];

            for (Annotation annotation1 : annotation.annotationType().getAnnotations()) {
                final Class<? extends Annotation> annotationType = annotation1.annotationType();
                if (annotationType.equals(IoDocsEnum.class)) {
                    return Lists.newArrayList(((IoDocsEnum) annotation1).value());
                }
            }

        }
        return Lists.newArrayList();
    }

    private List<String> getEnumDescriptionsToOneLevelOfNesting(final Annotation[] parameterAnnotations) {

        for (int i = 0; i < parameterAnnotations.length; i++) {
            final Annotation annotation = parameterAnnotations[i];
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(IoDocsEnumDescriptions.class)) {
                return Lists.newArrayList(((IoDocsEnumDescriptions) annotation).value());
            }
        }

        for (int i = 0; i < parameterAnnotations.length; i++) {

            final Annotation annotation = parameterAnnotations[i];

            for (Annotation annotation1 : annotation.annotationType().getAnnotations()) {
                final Class<? extends Annotation> annotationType = annotation1.annotationType();
                if (annotationType.equals(IoDocsEnumDescriptions.class)) {
                    return Lists.newArrayList(((IoDocsEnumDescriptions) annotation1).value());
                }
            }

        }
        return Lists.newArrayList();
    }

    private class LocationDetails {
        String name;
        Parameter.Location location;
        boolean isRequired;

        private LocationDetails(final String name, final Parameter.Location location,
                                final boolean isRequired) {
            this.name = name;
            this.location = location;
            this.isRequired = isRequired;
        }
    }


    private Object getDefault(final Annotation[] parameterAnnotations) {
        for (int i = 0; i < parameterAnnotations.length; i++) {
            final Annotation annotation = parameterAnnotations[i];
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(RequestParam.class)) {
                //Rather annoyingly if a default is not defined then it is not calculated so break out of loop and look
                //for in other annotations.
                final String defaultValue = ((RequestParam) annotation).defaultValue();
                if (!defaultValue.equals(ValueConstants.DEFAULT_NONE)) {
                    return defaultValue;
                }
                break;
            }
        }
        for (int i = 0; i < parameterAnnotations.length; i++) {

            final Annotation annotation = parameterAnnotations[i];
            final Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.equals(IoDocsDefaultBoolean.class)) {
                return ((IoDocsDefaultBoolean) annotation).value() + "";
            } else if (annotationType.equals(IoDocsDefaultInteger.class)) {
                return ((IoDocsDefaultInteger) annotation).value();
            } else if (annotationType.equals(IoDocsDefaultString.class)) {
                return ((IoDocsDefaultString) annotation).value();
            }
        }
        return "";
    }

    private Parameter.Type getType(final Class<?> typeClass) {
        if (Integer.class.isAssignableFrom(typeClass) || int.class.isAssignableFrom(typeClass))
            return Parameter.Type.INT;
        if (Boolean.class.isAssignableFrom(typeClass) || boolean.class.isAssignableFrom(typeClass))
            return Parameter.Type.BOOLEAN;
        final Type[] genericInterfaces = typeClass.getGenericInterfaces();
        for (Type type : genericInterfaces) {
            if (TypedParameter.class.isAssignableFrom(typeClass)) {
                final Class<?> paramType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                if (Integer.class.isAssignableFrom(paramType) || int.class.isAssignableFrom(paramType))
                    return Parameter.Type.INT;
                if (Boolean.class.isAssignableFrom(paramType) || boolean.class.isAssignableFrom(paramType))
                    return Parameter.Type.BOOLEAN;
            }
        }
        return Parameter.Type.STRING;
    }

    private boolean paramIsIgnored(final Annotation[] parameterAnnotations) {
        boolean ignore = false;
        for (Annotation parameterAnnotation : parameterAnnotations) {
            if (parameterAnnotation.annotationType().equals(IoDocsIgnore.class)) {
                ignore = true;
            }
        }
        return ignore;
    }
}
