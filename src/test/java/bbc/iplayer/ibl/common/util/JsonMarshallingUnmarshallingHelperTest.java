package bbc.iplayer.ibl.common.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import bbc.iplayer.ibl.common.SimpleChildEntity;
import bbc.iplayer.ibl.common.SimpleParentEntity;

public class JsonMarshallingUnmarshallingHelperTest {

	private static final String SAMPLE_JSON_FILENAME = "target/test-classes/fixtures/Simple.json";
	private static final String SAMPLE_JSON_DATA;
	static {
		try {
			SAMPLE_JSON_DATA = FileResourcesHelper.readDataFromFile(new File(SAMPLE_JSON_FILENAME), false, false);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static SimpleChildEntity child1 = new SimpleChildEntity("aChild1PropertyValue", "anotherChild1PropertyValue", 1);
	private static SimpleChildEntity child2 = new SimpleChildEntity("aChild2PropertyValue", "anotherChild2PropertyValue", 2);
	private static SimpleChildEntity child3 = new SimpleChildEntity("aChild3PropertyValue", "anotherChild3PropertyValue", 3);
	private static SimpleParentEntity parent = new SimpleParentEntity("aPropertyValue", "anothePropertyValue", Arrays.asList(child1, child2, child3), 0L);

	@Test
	public void testMarshallEntityToJson() throws Exception {
		SimpleParentEntity jsonObject = (SimpleParentEntity) JsonMarshallingUnmarshallingHelper.unmarshallJsonToEntity(SAMPLE_JSON_DATA, SimpleParentEntity.class, "UTF-8");
		String jsonMarshalledData = JsonMarshallingUnmarshallingHelper.marshallEntityToJson(jsonObject, "UTF-8");
		JSONAssert.assertEquals(SAMPLE_JSON_DATA, jsonMarshalledData, false);
	}

	@Test
	public void testUnmarshallJsonToEntity() throws Exception {
		SimpleParentEntity jsonObject = (SimpleParentEntity) JsonMarshallingUnmarshallingHelper.unmarshallJsonToEntity(SAMPLE_JSON_DATA, SimpleParentEntity.class, "UTF-8");
		assertEquals(parent.hashCode(), jsonObject.hashCode());
	}
}
