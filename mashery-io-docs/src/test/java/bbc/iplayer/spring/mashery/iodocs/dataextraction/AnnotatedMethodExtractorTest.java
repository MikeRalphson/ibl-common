package bbc.iplayer.spring.mashery.iodocs.dataextraction;

import bbc.iplayer.spring.mashery.iodocs.SampleController;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnnotatedMethodExtractorTest extends AbstractExtractorTest {


    private Class<?> aClazz;
    private List<Method> methods;

    @Before
    public void setUp() throws Exception {
        aClazz = SampleController.class;
        methods = new AnnotatedMethodExtractor().annotatedMethods(aClazz);
    }

    @Test
    public void ignoredMethodsNotIncluded() {
        final Method method = findMethod("ignoredMethod");
        assertThat(method, is(nullValue()));
    }

    private Method findMethod(final String methodName) {
        return Iterables.find(methods, new Predicate<Method>() {
            @Override
            public boolean apply(Method input) {
                return input.getName().equals(methodName);
            }
        }, null);
    }

    @Test
    public void requestMappingAnnotationNeeded() {
        final Method method = findMethod("methodWithNoAnnotations");
        assertThat(method, is(nullValue()));

        final Method methodWithRequestMapping = findMethod("getByInitialCharacter");
        assertThat(methodWithRequestMapping, is(notNullValue()));
    }


}
