package bbc.iplayer.spring.mashery.iodocs.dataextraction;

import bbc.iplayer.spring.mashery.iodocs.SampleController;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DescriptionExtractorTest extends AbstractExtractorTest {

    private Class<?> aClazz;
    private Method mainMethod;
    private DescriptionExtractor descriptionExtractor;

    @Before
    public void setUp() throws Exception {
        aClazz = SampleController.class;
        mainMethod = getMethodByName(aClazz, "getByInitialCharacter");
        descriptionExtractor = new DescriptionExtractor();
    }

    @Test
    public void descriptionPulledFromMethodAnnotation() {

        String description = descriptionExtractor.getDescription(mainMethod).get();
        assertThat(description, is(equalTo("Get the Programmes whose title begins with the given initial character.")));
    }

    @Test
    public void descriptionsPulledFromParameterAnnotation() {
        String description = descriptionExtractor.getDescription(mainMethod.getParameterAnnotations()[1]).get();
        assertThat(description, is(equalTo("The rights group to limit results to.")));
    }

    @Test
    public void descriptionsPulledFromParameterNestedAnnotation() {
        mainMethod = getMethodByName(aClazz, "withAWrappingAnnotation");
        String description = descriptionExtractor.getDescription(mainMethod.getParameterAnnotations()[0]).get();
        assertThat(description, is(equalTo("Know your ABC")));
    }


}
