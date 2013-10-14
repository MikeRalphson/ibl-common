package bbc.iplayer.ibl.common.util;

import java.io.File;
import java.util.Arrays;

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

import bbc.iplayer.ibl.common.SimpleChildEntity;
import bbc.iplayer.ibl.common.SimpleParentEntity;

public class XmlMarshallingUnmarshallingHelperTest extends XMLTestCase {

	private static final String SAMPLE_XML_FILENAME = "target/test-classes/fixtures/util/Simple.xml";
	private static final String SAMPLE_XML_DATA;
	static {
		try {
			SAMPLE_XML_DATA = FileResourcesHelper.readDataFromFile(new File(SAMPLE_XML_FILENAME), false, false);
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
	public void testMarshallEntityToXml() throws Exception {
		XMLUnit.setIgnoreWhitespace(true);
		SimpleParentEntity xmlObject = (SimpleParentEntity) XmlMarshallingUnmarshallingHelper.unmarshallXmlToEntity(SAMPLE_XML_DATA, SimpleParentEntity.class, "UTF-8");
		String xmlMarshalledData = XmlMarshallingUnmarshallingHelper.marshallEntityToXml(xmlObject, "UTF-8");
		assertXMLEqual(SAMPLE_XML_DATA, xmlMarshalledData);
	}

	@Test
	public void testUnmarshallXmlToEntity() throws Exception {
		XMLUnit.setIgnoreWhitespace(true);
		SimpleParentEntity xmlObject = (SimpleParentEntity) XmlMarshallingUnmarshallingHelper.unmarshallXmlToEntity(SAMPLE_XML_DATA, SimpleParentEntity.class, "UTF-8");
		assertEquals(parent.hashCode(), xmlObject.hashCode());
	}
}
