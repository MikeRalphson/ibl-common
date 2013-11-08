package bbc.iplayer.ibl.common.model.impl;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValuePairMap;

/**
 * <p>
 * 	This (abstract) class subclasses <code>bbc.iplayer.ibl.common.model.impl.AbstractVersionedElement</code>
 * 	and is intended to be further subclassed by any (persistable) entity.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.impl.AbstractVersionedElement
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
