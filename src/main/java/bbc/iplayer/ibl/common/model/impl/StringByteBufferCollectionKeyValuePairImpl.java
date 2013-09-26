package bbc.iplayer.ibl.common.model.impl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValuePair;

/**
 * <p>
 * 	This class provides an alternative implementation of the KeyValuePair interface.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a Collection<ByteBuffer></li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePair
 *
 */
@XmlTransient
@JsonIgnoreType
public class StringByteBufferCollectionKeyValuePairImpl
implements KeyValuePair<String, Collection<ByteBuffer>> {

	private static final long serialVersionUID = 1L;

	private String key; // used to store the key of the (key, value) pair
	private Collection<ByteBuffer> value; // used to store the value of the (key, value) pair

	/**
	 * <p>
	 * 	Default constructor that creates an instance of StringByteBufferCollectionKeyValuePairImpl
	 * 	with empty (zero-length String) key and empty collection of String for value
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value.isEmpty(): true
	 */
	public StringByteBufferCollectionKeyValuePairImpl() {
		super();
		clear();
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of StringByteBufferCollectionKeyValuePairImpl with the
	 * 	key and value provided
	 * </p>
	 *
	 * @param key the key to be used for this KeyValuePair
	 * @param value the value to be used for this KeyValuePair
	 *
	 * @post: this.key != null: true
	 * @post: this.key == key || this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == value || this.value.isEmpty(): true
	 */
	public StringByteBufferCollectionKeyValuePairImpl(String key, Collection<ByteBuffer> value) {
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
	public void setKey(String key) {
		if (key == null) {
			this.key = "";
		}
		else {
			this.key = key;
		}
	}

	@Override
	public Collection<ByteBuffer> getValue() {
		return value;
	}

	@Override
	/**
	 * @post: this.value != null: true
	 */
	public void setValue(Collection<ByteBuffer> value) {
		if (value == null) {
			this.value = new ArrayList<ByteBuffer>();
		}
		else {
			this.value = value;
		}
	}

	@Override
	public boolean isValid() {
		return ((getKey() != null) && (getKey().length() != 0));
	}

	@Override
	public boolean isEmpty() {
		return ((getKey().length() == 0) && (getValue().isEmpty()));
	}

	@Override
	public void clear() {
		setKey("");
		setValue(new ArrayList<ByteBuffer>());
	}

	@Override
	public KeyValuePair<String, Collection<ByteBuffer>> clone() {
		KeyValuePair<String, Collection<ByteBuffer>> clonedObject = null;

		clonedObject = new StringByteBufferCollectionKeyValuePairImpl(getKey(), getValue());

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
		StringByteBufferCollectionKeyValuePairImpl other = (StringByteBufferCollectionKeyValuePairImpl) obj;
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
