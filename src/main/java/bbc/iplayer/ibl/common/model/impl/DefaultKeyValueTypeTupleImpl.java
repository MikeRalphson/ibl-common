/**
 *
 */
package bbc.iplayer.ibl.common.model.impl;

import java.io.Serializable;

import bbc.iplayer.ibl.common.model.KeyValueTypeTuple;

/**
 * <p>
 * 	This class provides a default implementation of the KeyValueTypeTuple
 * 	interface.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 		<li>type is a Class<? extends Serializable></li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTuple
 *
 */
public class DefaultKeyValueTypeTupleImpl
extends DefaultKeyValuePairImpl
implements KeyValueTypeTuple<String, String, Class<? extends Serializable>> {

	private static final long serialVersionUID = 1L;

	private Class<? extends Serializable> type; // used to store the type of the (key, value, type) tuple

	/**
	 * <p>
	 * 	Default constructor that creates an instance of DefaultKeyValueTypeTupleImpl
	 * 	with empty (zero-length String) key and value and Object.class as the type
	 * </p>
	 *
	 * @post: this.key != null: true
	 * @post: this.key == "": true
	 * @post: this.value != null: true
	 * @post: this.value == "": true
	 * @post: this.type != null: true
	 * @post: this.type == Serializable.class: true
	 */
	public DefaultKeyValueTypeTupleImpl() {
		super();
		clear();
		setType(null);
	}

	/**
	 * <p>
	 * 	Constructor that creates an instance of DefaultKeyValueTypeTupleImpl with the
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
	 * @post: this.type == type || this.type == Serializable.class: true
	 */
	public DefaultKeyValueTypeTupleImpl(String key, String value, Class<? extends Serializable> type) {
		super(key, value);
		setType(type);
	}

	@Override
	public Class<? extends Serializable> getType() {
		return type;
	}

	@Override
	public void setType(Class<? extends Serializable> type) {
		if (type == null) {
			this.type = Serializable.class;
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
	public KeyValueTypeTuple<String, String, Class<? extends Serializable>> clone() {
		KeyValueTypeTuple<String, String, Class<? extends Serializable>> clonedObject = null;

		clonedObject = new DefaultKeyValueTypeTupleImpl(getKey(), getValue(), getClass());

		return clonedObject;
	}
}
