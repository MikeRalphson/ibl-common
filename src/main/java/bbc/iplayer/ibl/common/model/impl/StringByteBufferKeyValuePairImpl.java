package bbc.iplayer.ibl.common.model.impl;

import java.nio.ByteBuffer;

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
 * 		<li>value is a ByteBuffer</li>
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
public class StringByteBufferKeyValuePairImpl
implements KeyValuePair<String, ByteBuffer> {

	private static final long serialVersionUID = 1L;

	private String key; // used to store the key of the (key, value) pair
	private ByteBuffer value; // used to store the value of the (key, value) pair

	/**
	 * <p>
	 * 	Default constructor that creates an instance of StringByteBufferKeyValuePairImpl
	 * 	with empty (zero-length String) key and value
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value.hasRemaining(): false
	 */
	public StringByteBufferKeyValuePairImpl() {
		super();
		clear();
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of StringByteBufferKeyValuePairImpl with the
	 * 	key and value provided
	 * </p>
	 *
	 * @param key the key to be used for this KeyValuePair
	 * @param value the value to be used for this KeyValuePair
	 *
	 * @post: this.key != null: true
	 * @post: this.key == key || this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == value || this.value.hasRemaining(): false
	 */
	public StringByteBufferKeyValuePairImpl(String key, ByteBuffer value) {
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
	public ByteBuffer getValue() {
		return value;
	}

	@Override
	/**
	 * @post: this.value != null: true
	 */
	public void setValue(ByteBuffer value) {
		if (value == null) {
			this.value = ByteBuffer.allocate(1024);
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
		return ((getKey().length() == 0) && (!getValue().hasRemaining()));
	}

	@Override
	public void clear() {
		setKey("");
		setValue(ByteBuffer.allocate(1024));
	}

	@Override
	public KeyValuePair<String, ByteBuffer> clone() {
		KeyValuePair<String, ByteBuffer> clonedObject = null;

		clonedObject = new StringByteBufferKeyValuePairImpl(getKey(), getValue());

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
		StringByteBufferKeyValuePairImpl other = (StringByteBufferKeyValuePairImpl) obj;
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
