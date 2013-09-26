package bbc.iplayer.ibl.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlMarshallingUnmarshallingHelper {

	public static Object unmarshallXmlToEntity(String entityXml, Class<?> entityClass, String encoding) throws Exception {
		Object result = null;
		JAXBContext tempJaxbContext = null;
		Unmarshaller tempJaxbUnmarshaller = null;
		ByteArrayInputStream tempBAIS = null;

		if (entityXml != null) {
			tempJaxbContext = JAXBContext.newInstance(entityClass);
			tempJaxbUnmarshaller = tempJaxbContext.createUnmarshaller();
			tempBAIS = new ByteArrayInputStream(entityXml.getBytes(encoding));
			result = tempJaxbUnmarshaller.unmarshal(tempBAIS);
		}

		return result;
	}

	public static byte[] marshallEntityToXmlBytes(Object entityObject, String encoding) throws Exception {
		byte[] result = null;
		JAXBContext tempJaxbContext = null;
		Marshaller tempJaxbMarshaller = null;
		ByteArrayOutputStream tempBAOS = null;

		if (entityObject != null) {
			tempJaxbContext = JAXBContext.newInstance(entityObject.getClass());
			tempJaxbMarshaller = tempJaxbContext.createMarshaller();
			tempJaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			tempJaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			tempBAOS = new ByteArrayOutputStream();
			tempJaxbMarshaller.marshal(entityObject, tempBAOS);
			result = tempBAOS.toByteArray();
		}

		return result;
	}

	public static String marshallEntityToXml(Object entityObject, String encoding) throws Exception {
		String result = null;

		if (entityObject != null) {
			result = new String(marshallEntityToXmlBytes(entityObject, encoding));
		}

		return result;
	}
}
