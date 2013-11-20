package bbc.iplayer.spring.mashery.iodocs.dataextraction;

import bbc.iplayer.spring.mashery.iodocs.annotations.IoDocsDescription;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class DescriptionExtractor {

    public static final Predicate<Annotation> ANNOTATION_PREDICATE = new Predicate<Annotation>() {
        @Override
        public boolean apply(Annotation input) {
            return input.annotationType().equals(IoDocsDescription.class);
        }
    };

    public Optional<String> getDescription(final Method method) {
        final IoDocsDescription annotation = method.getAnnotation(IoDocsDescription.class);
        if (annotation != null) {
            return Optional.of(annotation.value());
        }
        return Optional.absent();
    }

    /**
     * Gets description to one level of nesting so a wrapping annotation can be used
     */
    public Optional<String> getDescription(final Annotation... annotations) {
        final Annotation found = Iterables.find(Lists.newArrayList(annotations), ANNOTATION_PREDICATE, null);
        if (found != null) {
            return Optional.of(((IoDocsDescription) found).value());
        }
        for (Annotation nestedAnnotation : annotations) {
            final ArrayList<Annotation> nestedAnnotationAnnotations = Lists.newArrayList(nestedAnnotation.annotationType().getAnnotations());
            final Annotation nestedFound = Iterables.find(nestedAnnotationAnnotations, ANNOTATION_PREDICATE, null);
            if (nestedFound != null) {
                return Optional.of(((IoDocsDescription) nestedFound).value());
            }
        }
        return Optional.absent();
    }
}
