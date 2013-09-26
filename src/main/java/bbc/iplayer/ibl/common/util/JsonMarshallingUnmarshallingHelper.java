package bbc.iplayer.ibl.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonMarshallingUnmarshallingHelper {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static Object unmarshallJsonToEntity(String entityJson, Class<?> entityClass, String encoding) throws Exception {
		Object result = null;
		ByteArrayInputStream tempBAIS = null;

		if (entityJson != null) {
			tempBAIS = new ByteArrayInputStream(entityJson.getBytes(encoding));
			result = objectMapper.readValue(tempBAIS, entityClass);
		}

		return result;
	}

	public static byte[] marshallEntityToJsonBytes(Object entityObject, String encoding) throws Exception {
		byte[] result = null;
		ByteArrayOutputStream tempBAOS = null;

		if (entityObject != null) {
			tempBAOS = new ByteArrayOutputStream();
			objectMapper.writeValue(tempBAOS, entityObject);
			result = tempBAOS.toByteArray();
		}

		return result;
	}

	public static String marshallEntityToJson(Object entityObject, String encoding) throws Exception {
		String result = null;
		if (entityObject != null) {
			result = new String(marshallEntityToJsonBytes(entityObject, encoding));
		}

		return result;
	}

	public static JsonNode marshallEntityToJsonNode(Object entityObject, String encoding) throws Exception {
		JsonNode result = null;
		String tempJsonString = null;

		if (entityObject != null) {
			tempJsonString = new String(marshallEntityToJsonBytes(entityObject, encoding));
			result = objectMapper.readTree(tempJsonString);
		}

		return result;
	}
}
