package uk.co.bbc.iplayer.common.model.impl;

import org.codehaus.jackson.annotate.JsonIgnoreType;
import uk.co.bbc.iplayer.common.model.KeyValuePairMap;

import javax.xml.bind.annotation.XmlTransient;

/**
 * <p>
 * This (abstract) class subclasses <code>uk.co.bbc.iplayer.common.model.impl.AbstractVersionedElement</code>
 * and is intended to be further subclassed by any (persistable) entity.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 * @see uk.co.bbc.iplayer.common.model.impl.AbstractVersionedElement
 */
@XmlTransient
@JsonIgnoreType
public abstract class AbstractElement<E extends AbstractElement<E>>
        extends AbstractVersionedElement<AbstractElement<E>> {

    private static final long serialVersionUID = 1L;

    public AbstractElement() {
        super();
        setVersion(0);
    }

    public AbstractElement(String key, String value) {
        super(key, value, null);
    }

    public AbstractElement(String key, String value, KeyValuePairMap<String, String> attributes) {
        super(key, value, attributes);
    }
}
