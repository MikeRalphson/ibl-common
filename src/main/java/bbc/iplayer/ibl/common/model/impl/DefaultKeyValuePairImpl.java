package bbc.iplayer.ibl.common.model.impl;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValuePair;

/**
 * <p>
 * 	This class provides a default implementation of the KeyValuePair interface.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePair
 */
@XmlTransient
@JsonIgnoreType
public class DefaultKeyValuePairImpl
implements KeyValuePair<String, String> {

	private static final long serialVersionUID = 1L;

	private String key; // used to store the key of the (key, value) pair
	private String value; // used to store the value of the (key, value) pair

	/**
	 * <p>
	 * 	Default constructor that creates an instance of DefaultKeyValuePairImpl
	 * 	with empty (zero-length String) key and value
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == "": true
	 */
	public DefaultKeyValuePairImpl() {
		super();
		setKey("");
		setValue("");
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of DefaultKeyValuePairImpl with the
	 * 	key and value provided
	 * </p>
	 *
	 * @param key the key to be used for this KeyValuePair
	 * @param value the value to be used for this KeyValuePair
	 *
	 * @post: this.key != null: true
	 * @post: this.key == key || this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == value || this.value == "": true
	 */
	public DefaultKeyValuePairImpl(String key, String value) {
		super();
		setKey(key);
		setValue(value);
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	/**
	 * @post: this.key != null: true
	 */
	public final void setKey(String key) {
		if (key == null) {
			this.key = "";
		}
		else {
			this.key = key;
		}
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	/**
	 * @post: this.value != null: true
	 */
	public final void setValue(String value) {
		if (value == null) {
			this.value = "";
		}
		else {
			this.value = value;
		}
	}

	@Override
	@XmlTransient
	@JsonIgnore	
	public boolean isValid() {
		return ((getKey() != null) && (getKey().length() != 0));
	}

	@Override
	@XmlTransient
	@JsonIgnore	
	public boolean isEmpty() {
		return ((getKey().length() == 0) && (getValue().length() == 0));
	}

	@Override
	public void clear() {
		setKey("");
		setValue("");
	}

	@Override
	public KeyValuePair<String, String> clone() {
		KeyValuePair<String, String> clonedObject = null;

		clonedObject = new DefaultKeyValuePairImpl(getKey(), getValue());

		return clonedObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultKeyValuePairImpl other = (DefaultKeyValuePairImpl) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		}
		else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}
}
