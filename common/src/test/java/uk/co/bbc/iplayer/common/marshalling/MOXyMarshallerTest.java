package uk.co.bbc.iplayer.common.marshalling;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.oxm.MediaType;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.UnmarshallingFailureException;
import uk.co.bbc.iplayer.common.utils.IOConversions;
import uk.co.bbc.iplayer.common.utils.fixtures.Fixtures;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class MOXyMarshallerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static SampleIBL iblResponse;
    private static Map<String, Object> properties = new HashMap<String, Object>();
    private static StreamSource recEngSource;
    private final static Class TEST_IBL_ROOT = SampleIBL.class;
    private final static Class TEST_REC_ENG_ROOT = SampleRootElementJson.class;
    private final static Class TEST_NITRO_ROOT = SampleNitro.class;
    private static final int DEFAULT_SAMPLE_ELEMENT_SIZE = 20;

    @BeforeClass
    public static void init() throws URISyntaxException, IOException {

        iblResponse = new SampleIBL();
        iblResponse.setVersion("1.0");
        iblResponse.setSchema(new URI("http://bbc.co.uk"));

        // Properties to MoxyMarshaller for JSON
        properties.put(JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

        recEngSource = Fixtures.createStreamSource("sampleelements.json");
    }

    @Test
    public void writeThenReadIBLResponseInXmlformat() throws IOException {
        SampleIBL actual = writeAndReadSameResponse(SampleIBL.class, iblResponse);
        assertIBLResponse(actual);
    }

    @Test
    public void writeThenReadIBLResponseInJsonformat() throws IOException {
        SampleIBL actual = writeAndReadSameResponse(SampleIBL.class, iblResponse, properties);
        assertIBLResponse(actual);
    }

    @Test
    public void unmarshallNitroResponse() throws IOException {
        Unmarshaller unmarshaller = new MOXyMarshaller(TEST_NITRO_ROOT);
        StreamSource content = Fixtures.createStreamSource("marshalling-counts.xml");
        final Object unmarshal = unmarshaller.unmarshal(content);
        SampleNitro nitro = (SampleNitro) unmarshal;

        assertThat(nitro, notNullValue());
    }

    @Test
    public void unmarshalWithNoJsonRoot() throws Exception {

        MOXyMarshaller moxy = new MOXyMarshaller(TEST_REC_ENG_ROOT);
        moxy.setUnmarshallerProperties(properties);
        moxy.setUnmarshallingRoot(TEST_REC_ENG_ROOT);

        SampleRootElementJson samples = (SampleRootElementJson) moxy.unmarshal(recEngSource);

        assertThat(samples, notNullValue());
        assertThat(samples.getSampleElements().size(), is(DEFAULT_SAMPLE_ELEMENT_SIZE));
    }

    @Test()
    public void unmarshalWithNoExplictRootDefined() throws Exception {

        expectedException.expect(UnmarshallingFailureException.class);
        expectedException.expectMessage("No root has been defined for unmarshalling");

        MOXyMarshaller moxy = new MOXyMarshaller(TEST_IBL_ROOT, TEST_REC_ENG_ROOT);
        moxy.setUnmarshallerProperties(properties);
        moxy.unmarshal(recEngSource);
    }

    @Test
    public void unmarshallWithUnsupportedRoot() {
        expectedException.expect(UnmarshallingFailureException.class);
        expectedException.expectMessage("Unsupported root");

        MOXyMarshaller moxy = new MOXyMarshaller(TEST_IBL_ROOT);
        moxy.setUnmarshallerProperties(properties);
        moxy.setUnmarshallingRoot(TEST_REC_ENG_ROOT);
        moxy.unmarshal(recEngSource);
    }

    private <T> T writeAndReadSameResponse(Class<T> type, T response, Map<String, Object> properties) {

        // Create marshaller with T
        MOXyMarshaller moxy = new MOXyMarshaller(type);
        if (properties != null) {
            moxy.setMarshallerProperties(properties);
            moxy.setUnmarshallerProperties(properties);
        }

        // write (marshal) the response to a Result and convert to String
        StringWriter output = new StringWriter();
        moxy.marshal(response, new StreamResult(output));
        String expected = output.toString();

        // read the output previously written
        return (T) moxy.unmarshal(
                IOConversions.toStreamSource(expected));
    }

    private <T> T writeAndReadSameResponse(Class<T> type, T response) {
        return writeAndReadSameResponse(type, response, null);
    }

    private void assertIBLResponse(SampleIBL actual) {
        String actualVersion = actual.getVersion();
        String actualSchema = actual.getSchema().toString();

        // IBL object returned should match the source IBL object
        assertThat(iblResponse.getVersion(), is(actualVersion));
        assertThat(iblResponse.getSchema().toString(), is(actualSchema));
    }

}