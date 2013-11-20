package bbc.iplayer.spring.mashery.iodocs.dataextraction;


import bbc.iplayer.spring.mashery.iodocs.SampleController;
import bbc.iplayer.spring.mashery.iodocs.model.Parameter;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParamExtractorTest extends AbstractExtractorTest {

    private Class<?> aClazz;
    private Method mainMethod;
    private ParamExtractor extractor;
    private List<Parameter> params;
    private Parameter initialCharParam;
    private Parameter rightsParam;
    private Parameter testRequiredParam;
    private Parameter testBooleanParam;
    private Parameter initialChildCount;
    private Parameter perPageParam;

    @Before
    public void setUp() throws Exception {
        aClazz = SampleController.class;
        mainMethod = getMethodByName(aClazz, "getByInitialCharacter");
        extractor = new ParamExtractor();
        params = extractor.getParamsFromMethod(mainMethod);
        initialCharParam = params.get(0);
        rightsParam = params.get(1);
        perPageParam = params.get(3);
        initialChildCount = params.get(4);
        testRequiredParam = params.get(5);
        testBooleanParam = params.get(6);
    }

    @Test
    public void initialCharacterMethodHasParams() {
        assertThat(params, hasSize(7));
    }

    @Test
    public void initialCharacterMethodInitialCharacter() {
        assertThat(initialCharParam.getType(), is(equalTo(Parameter.Type.STRING)));
        assertThat(initialCharParam.isRequired(), is(equalTo(true)));
        assertThat((String) initialCharParam.getDefaultValue(), is(equalTo("a")));
        assertThat(initialCharParam.getDescription(), is(equalTo("The programme identifier.")));
        assertThat(initialCharParam.getLocation(), is(equalTo(Parameter.Location.pathReplace)));
        assertThat(initialCharParam.getName(), is(equalTo(":character")));
        assertThat(initialCharParam.isRequired(), is(true));
        assertThat(initialCharParam.getEnumeration(), hasSize(27));
        assertThat(initialCharParam.getEnumDescriptions(), hasSize(0));
    }

    @Test
    public void defaultInitialChildCountIs4() {
        assertThat((String) initialChildCount.getDefaultValue(), is(equalTo("4")));
    }

    @Test
    public void requiredIsFalseForPerPage() {
        assertThat(perPageParam.isRequired(), is(false));
    }

    @Test
    public void initialCharacterMethodRightsHasADescription() {
        assertThat(rightsParam.getDescription(), is(equalTo("The rights group to limit results to.")));
        assertThat(rightsParam.isRequired(), is(true));
        assertThat(rightsParam.getEnumeration(), hasItems("", "mobile", "tv", "web"));
        assertThat(rightsParam.getEnumDescriptions(), hasItems("", "mobile", "television", "web"));
        assertThat((String) rightsParam.getDefaultValue(), is(equalTo("web")));
    }

    @Test
    public void testRequiredParamIsRequired() {
        assertThat(testRequiredParam.isRequired(), is(true));
    }

    @Test
    public void testBooleanParamHasEnums() {
        assertThat(testBooleanParam.getType(), is(equalTo(Parameter.Type.STRING)));
        assertThat(testBooleanParam.getEnumeration(), hasItems("false", "true"));
        assertThat(testBooleanParam.getEnumDescriptions(), hasItems("false", "true"));
    }

    @Test
    public void wrappedAnnotationParamHasValues() {
        mainMethod = getMethodByName(aClazz, "withAWrappingAnnotation");
        extractor = new ParamExtractor();
        params = extractor.getParamsFromMethod(mainMethod);
        final Parameter parameter = params.get(0);
        assertThat(parameter.getEnumeration(), hasItems("a", "b", "c"));
        assertThat(parameter.getEnumDescriptions(), hasItems("aDesc", "bDesc", "cDesc"));
        assertThat(parameter.getDescription(), is(equalTo("Know your ABC")));


    }
}
