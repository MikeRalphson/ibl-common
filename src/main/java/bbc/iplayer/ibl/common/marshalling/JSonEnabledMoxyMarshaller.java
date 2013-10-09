package bbc.iplayer.ibl.common.marshalling;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.oxm.MediaType;

import java.util.HashMap;
import java.util.Map;

public class JSonEnabledMoxyMarshaller extends MOXyMarshaller {

    public JSonEnabledMoxyMarshaller(Class<?>... clss) {

        super(clss);

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(JAXBContextProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, false);

        setMarshallerProperties(properties);
        setUnmarshallerProperties(properties);
    }
}
