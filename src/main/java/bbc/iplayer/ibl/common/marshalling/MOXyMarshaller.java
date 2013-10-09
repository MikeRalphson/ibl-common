package bbc.iplayer.ibl.common.marshalling;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.springframework.oxm.UnmarshallingFailureException;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author spragn01
 * @since 0.3.0
 */
public class MOXyMarshaller extends Jaxb2Marshaller {

    private JAXBContext jaxbContext;
    private final AtomicBoolean unmarshallWithoutRoot = new AtomicBoolean(false);
    private final AtomicReference<Class> root = new AtomicReference<Class>();

    public MOXyMarshaller(Class<?>... classesToBeBound) {
        setClassesToBeBound(classesToBeBound);
    }

    /**
     * Creates a JAXBContext, explicitly using the MOXy implementation,
     * which can read/write JSON (if configured) and XML (default)
     * <p/>
     * NOTE - Metro (reference implementation) does not support JSON
     */
    @Override
    protected synchronized JAXBContext getJaxbContext() {

        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContextFactory.createContext(
                        getClassesToBeBound(), null);

            } catch (JAXBException ex) {
                throw convertJaxbException(ex);
            }
        }

        return jaxbContext;
    }

    @Override
    public Object unmarshal(Source source) throws XmlMappingException {

        if (unmarshallWithoutRoot.get()) {
            Unmarshaller unmarshaller = createUnmarshaller();
            try {
                Class root = getRoot();
                return unmarshaller.unmarshal(source, root).getValue();
            } catch (JAXBException e) {
                throw convertJaxbException(e);
            }
        }

        return super.unmarshal(source);
    }

    @Override
    public void setUnmarshallerProperties(Map<String, ?> properties) {

        unmarshallWithoutRoot.set(false);

        for (Map.Entry<String, ?> entrySet : properties.entrySet()) {
            String key = entrySet.getKey();

            // root excluded, then a root is required for unmarshalling
            if (key.equals(JAXBContextProperties.JSON_INCLUDE_ROOT)) {
                Boolean includeRoot = (Boolean) entrySet.getValue();
                if (!includeRoot) {
                    unmarshallWithoutRoot.set(true);
                }
            }
        }

        super.setUnmarshallerProperties(properties);
    }

    public void setUnmarshallingRoot(Class klass) {

        if (!this.supports(klass)) {
            throw new UnmarshallingFailureException("Unsupported root");
        }
        this.root.set(klass);
    }

    private Class getRoot() throws UnmarshalException {

        // attempt to use explicitly set root
        Class klass = root.get();
        if (klass != null) {
            return klass;

        } else {
            Class[] classes = getClassesToBeBound();
            // only one class bound, so this has to be the root
            if (classes.length == 1) {
                return classes[0];
            }

            throw new javax.xml.bind.UnmarshalException("No root has been defined for unmarshalling");
        }

    }

}