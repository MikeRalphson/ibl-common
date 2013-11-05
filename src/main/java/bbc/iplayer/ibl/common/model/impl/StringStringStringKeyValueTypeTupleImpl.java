package bbc.iplayer.ibl.common.model.impl;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValueTypeTuple;

/**
 * <p>
 * 	This class provides an alternative implementation of the KeyValueTypeTuple
 * 	interface.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 		<li>type is a String</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTuple
 */
@XmlTransient
@JsonIgnoreType
public class StringStringStringKeyValueTypeTupleImpl
extends DefaultKeyValuePairImpl
implements KeyValueTypeTuple<String, String, String> {

	private static final long serialVersionUID = 1L;

	private String type; // used to store the type of the (key, value, type) tuple

	/**
	 * <p>
	 * 	Default constructor that creates an instance of StringStringStringKeyValueTypeTupleImpl
	 * 	with empty (zero-length String) key and value and Object.class as the type
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == "": true
	 * @post: this.type != null: true
	 * @post: this.type == "": true
	 */
	public StringStringStringKeyValueTypeTupleImpl() {
		super();
		clear();
		setType(null);
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of StringStringStringKeyValueTypeTupleImpl with the
	 * 	key, value and type provided
	 * </p>
	 *
	 * @param key the key to be used for this KeyValueTypeTuple
	 * @param value the value to be used for this KeyValueTypeTuple
	 * @param type the type to be used for this KeyValueTypeTuple
	 *
	 * @post: this.key != null: true
	 * @post: this.key == key || this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == value || this.value == "": true
	 * @post: this.type != null: true
	 * @post: this.type == type || this.type == "": true
	 */
	public StringStringStringKeyValueTypeTupleImpl(String key, String value, String type) {
		super(key, value);
		setType(type);
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		if (type == null) {
			this.type = "";
		}
		else {
			this.type = type;
		}
	}

	@Override
	public boolean isEmpty() {
		return (super.isEmpty() && (getType() == null));
	}

	@Override
	public void clear() {
		super.clear();
		setType(null);
	}

	@Override
	public KeyValueTypeTuple<String, String, String> clone() {
		KeyValueTypeTuple<String, String, String> clonedObject = null;

		clonedObject = new StringStringStringKeyValueTypeTupleImpl(getKey(), getValue(), getType());

		return clonedObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		StringStringStringKeyValueTypeTupleImpl other = (StringStringStringKeyValueTypeTupleImpl) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		}
		else if (!type.equals(other.type))
			return false;
		return true;
	}
}
