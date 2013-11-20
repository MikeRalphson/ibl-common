package bbc.iplayer.spring.mashery.iodocs;

import bbc.iplayer.spring.mashery.iodocs.model.Auth;
import bbc.iplayer.spring.mashery.iodocs.model.IoDocAPI;
import bbc.iplayer.spring.mashery.iodocs.model.Key;
import bbc.iplayer.spring.mashery.iodocs.model.Parameter;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SpringIoDocsGeneratorTest {


    public static final ArrayList<String> NO_ENUM_DESCRIPTIONS = Lists.<String>newArrayList();
    public static final boolean REQUIRED = true;

    @Test
    public void sampleControllerGeneratesJSON() throws IOException, JSONException {
        final File fileFromClassPath = FileData.getFileFromClassPath("iodocs/iodocs-atoz.json");
        String expectedJson = Files.toString(fileFromClassPath, Charsets.UTF_8);
        final String api = api();
        JSONObject expected = new JSONObject(expectedJson);
        JSONObject actual = new JSONObject(api);
        System.out.println(api);
        JSONAssert.assertEquals(expected, actual, false);

    }

    public String api() {
        final IoDocAPI ioDocAPI = new IoDocAPI(new Auth(new Key("api_key", "query")), "rest",
                "http://d.bbc.co.uk", "BBC iPlayer API",
                "The definitive iPlayer API.", "1.0",
                "iPlayer-API", "/:env/tviplayer/:version");

        final Parameter envParam = new Parameter(
                "env", "The environment the API is in.",
                Parameter.Location.pathReplace, Parameter.Type.STRING,
                REQUIRED, "int", Lists.newArrayList("int", "test", "stage", ""), NO_ENUM_DESCRIPTIONS);
        final Parameter versionParam = new Parameter(
                "version", "The API major version.",
                Parameter.Location.pathReplace, Parameter.Type.STRING,
                REQUIRED, "v1", Lists.newArrayList("v1"), NO_ENUM_DESCRIPTIONS);
        return new SpringIoDocsGenerator(ioDocAPI).generateIoDocs(
                new Class<?>[]{
                        SampleController.class,
                },
                Lists.newArrayList(envParam, versionParam));
    }
}
