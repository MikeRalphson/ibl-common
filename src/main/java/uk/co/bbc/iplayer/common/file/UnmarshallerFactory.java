package uk.co.bbc.iplayer.common.file;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.util.HashMap;
import java.util.Map;

@Deprecated
public class UnmarshallerFactory {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UnmarshallerFactory.class);

    public static Unmarshaller getUnmarshaller(Class aClass) throws UnmarshallerCreationException {
        return getUnmarshaller(aClass, null);
    }

    public static Unmarshaller getUnmarshaller(Class aClass, Map<String, Object> properties) throws UnmarshallerCreationException {
        Unmarshaller unmarshaller;

        try {
            JAXBContext jc = JAXBContextFactory.createContext(new Class[]{aClass}, properties);
            unmarshaller = jc.createUnmarshaller();
        } catch (JAXBException ex) {
            LOG.error("Cannot create unmarshaller for " + aClass.getName(), ex);
            throw new UnmarshallerCreationException();
        }

        return unmarshaller;
    }

    public static Unmarshaller getJsonUnmarshaller(Class aClass) throws UnmarshallerCreationException {
        Map<String, Object> properties = new HashMap<String, Object>(2);

        properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

        return getUnmarshaller(aClass, properties);
    }
}
