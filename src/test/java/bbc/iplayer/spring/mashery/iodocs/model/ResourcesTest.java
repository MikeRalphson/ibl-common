package bbc.iplayer.spring.mashery.iodocs.model;

import bbc.iplayer.spring.mashery.iodocs.FileData;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.IOException;

public class ResourcesTest {

    @Test
    public void jsonCorrect() throws IOException, JSONException {

        final File fileFromClassPath = FileData.getFileFromClassPath("iodocs/iodocs-singleresource.json");
        String expectedJson = Files.toString(fileFromClassPath, Charsets.UTF_8);
        final Resources resources = new Resources();
        resources.addMethod(new ResourceName("A to Z"), new ResourceMethod("Find", "GET", "Desc", "aPath", Lists.<Parameter>newArrayList()));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JSONObject actual = new JSONObject(gson.toJson(resources));
        JSONObject expected = new JSONObject(expectedJson);
        JSONAssert.assertEquals(expected, actual, false);
    }


}
