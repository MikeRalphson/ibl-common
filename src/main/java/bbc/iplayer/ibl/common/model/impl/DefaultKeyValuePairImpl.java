package bbc.iplayer.ibl.common.model.impl;

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
 *
 */
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
		clear();
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
	public void setKey(String key) {
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
	public void setValue(String value) {
		if (value == null) {
			this.value = "";
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
}
