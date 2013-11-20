package uk.co.bbc.iplayer.common.model.impl;

import org.codehaus.jackson.annotate.JsonIgnoreType;
import uk.co.bbc.iplayer.common.model.KeyValuePairMap;

import javax.xml.bind.annotation.XmlTransient;

/**
 * <p>
 * This (abstract) class subclasses <code>uk.co.bbc.iplayer.common.model.impl.DefaultKeyValueAttributesTupleImpl</code>
 * and is intended to be further subclassed by any (persistable) entity that needs to be versioned.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 * @see uk.co.bbc.iplayer.common.model.impl.DefaultKeyValueAttributesTupleImpl
 */
@XmlTransient
@JsonIgnoreType
public abstract class AbstractVersionedElement<T extends AbstractVersionedElement<T>>
        extends DefaultKeyValueAttributesTupleImpl {

    private static final long serialVersionUID = 1L;

    public static final String ATTRIBUTE_KEY_VERSION = "version";
    public static final String ATTRIBUTE_KEY_LAST_UPDATED_IN_MS = "lastUpdatedInMs";

    /**
     * Default no argument constructor.
     */
    public AbstractVersionedElement() {
        super();
        setVersion(0);
        setLastUpdatedInMs(System.currentTimeMillis());
    }

    public AbstractVersionedElement(String key, String value) {
        super(key, value, null);
        setVersion(0);
        setLastUpdatedInMs(System.currentTimeMillis());
    }

    public AbstractVersionedElement(String key, String value, KeyValuePairMap<String, String> attributes) {
        super(key, value, attributes);
        setVersion(0);
        setLastUpdatedInMs(System.currentTimeMillis());
    }

    public Integer getVersion() {
        Integer result = 0;
        String resultFromAttributes = getAttributes().get(ATTRIBUTE_KEY_VERSION);
        if (resultFromAttributes != null) {
            try {
                result = Integer.valueOf(resultFromAttributes);
            } catch (NumberFormatException nfe) {
                // ignore and default to 0
            }
        }
        return result;
    }

    public final void setVersion(Integer version) {
        if (version == null) {
            getAttributes().put(ATTRIBUTE_KEY_VERSION, new Integer(1).toString());
        } else {
            getAttributes().put(ATTRIBUTE_KEY_VERSION, version.toString());
        }
    }

    public Long getLastUpdatedInMs() {
        Long result = 0L;
        String resultFromAttributes = getAttributes().get(ATTRIBUTE_KEY_LAST_UPDATED_IN_MS);
        if (resultFromAttributes != null) {
            try {
                result = Long.valueOf(resultFromAttributes);
            } catch (NumberFormatException nfe) {
                // ignore and default to 0L
            }
        }
        return result;
    }

    public final void setLastUpdatedInMs(Long lastUpdatedInMs) {
        if (lastUpdatedInMs == null) {
            getAttributes().put(ATTRIBUTE_KEY_LAST_UPDATED_IN_MS, new Long(0).toString());
        } else {
            getAttributes().put(ATTRIBUTE_KEY_LAST_UPDATED_IN_MS, lastUpdatedInMs.toString());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getLastUpdatedInMs() == null) ? 0 : getLastUpdatedInMs().hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractVersionedElement other = (AbstractVersionedElement) obj;
        if (getVersion() == null) {
            if (other.getVersion() != null)
                return false;
        } else if (!getVersion().equals(other.getVersion()))
            return false;
        if (getLastUpdatedInMs() == null) {
            if (other.getLastUpdatedInMs() != null)
                return false;
        } else if (!getLastUpdatedInMs().equals(other.getLastUpdatedInMs()))
            return false;
        return true;
    }
}
