package bbc.iplayer.ibl.common.model.impl;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValueAttributesTuple;
import bbc.iplayer.ibl.common.model.KeyValuePairMap;

/**
 * <p>
 * 	This class provides a default implementation of the KeyValueAttributesTuple
 * 	interface.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 		<li>attributes is a DefaultKeyValuePairMapImpl</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueAttributesTuple
 * @see bbc.iplayer.ibl.common.model.KeyValuePairMap
 * @see bbc.iplayer.ibl.common.model.impl.DefaultKeyValuePairMapImpl
 */
@XmlTransient
@JsonIgnoreType
public class DefaultKeyValueAttributesTupleImpl
extends DefaultKeyValuePairImpl
implements KeyValueAttributesTuple<String, String> {

	private static final long serialVersionUID = 1L;

	private KeyValuePairMap<String, String> attributes; // used to store the attributes of the (key, value, attributes) tuple

	/**
	 * <p>
	 * 	Default constructor that creates an instance of DefaultKeyValueAttributesTupleImpl
	 * 	with empty (zero-length String) key and value and an empty List of attributes
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == "": true
	 * @post: this.attributes != null: true
	 * @post: this.attributes.isEmpty(): true
	 */
	public DefaultKeyValueAttributesTupleImpl() {
		super();
		clear();
		setAttributes(null);
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of DefaultKeyValueAttributesTupleImpl with the
	 * 	key, value and attributes provided
	 * </p>
	 *
	 * @param key the key to be used for this KeyValueAttributesTuple
	 * @param value the value to be used for this KeyValueAttributesTuple
	 * @param attributes the attributes to be used for this KeyValueAttributesTuple
	 *
	 * @post: this.key != null: true
	 * @post: this.key == key || this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == value || this.value == "": true
	 * @post: this.attributes != null: true
	 */
	public DefaultKeyValueAttributesTupleImpl(String key, String value, KeyValuePairMap<String, String> attributes) {
		super(key, value);
		setAttributes(attributes);
	}

	@Override
	@XmlTransient
	@JsonIgnore
	public KeyValuePairMap<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(KeyValuePairMap<String, String> attributes) {
		if (attributes == null) {
			this.attributes = new DefaultKeyValuePairMapImpl();
		}
		else {
			this.attributes = attributes;
		}
	}

	@Override
	@XmlTransient
	@JsonIgnore	
	public boolean isEmpty() {
		return (super.isEmpty() && (getAttributes().isEmpty()));
	}

	@Override
	public void clear() {
		super.clear();
		setAttributes(new DefaultKeyValuePairMapImpl());
	}

	@Override
	public KeyValueAttributesTuple<String, String> clone() {
		KeyValueAttributesTuple<String, String> clonedObject = null;

		clonedObject = new DefaultKeyValueAttributesTupleImpl(getKey(), getValue(), getAttributes());

		return clonedObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultKeyValueAttributesTupleImpl other = (DefaultKeyValueAttributesTupleImpl) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}
}
