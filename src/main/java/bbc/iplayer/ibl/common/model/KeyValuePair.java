package bbc.iplayer.ibl.common.model;

import java.io.Serializable;

/**
 * <p>
 * 	This interface represents a simple (key, value) pair where the key and the
 * 	value are both represented as objects.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePairList
 * @see bbc.iplayer.ibl.common.model.KeyValuePairMap
 */
public interface KeyValuePair<K extends Object, V extends Object>
extends Serializable {

	/**
	 * @return the key of this (key, value) pair
	 */
	public abstract K getKey();

	/**
	 * @param key to be used for this (key, value) pair
	 */
	public abstract void setKey(K key);

	/**
	 * @return the value of this (key, value) pair
	 */
	public abstract V getValue();

	/**
	 * @param value to be used for this (key, value) pair
	 */
	public abstract void setValue(V value);

	/**
	 * @return true if the key is not null
	 */
	public abstract boolean isValid();

	/**
	 * @return true if the key and the value are not null or zero-length, false otherwise
	 */
	public abstract boolean isEmpty();

	/**
	 * clears (sets to null) the value of this (key, value) pair
	 */
	public abstract void clear();

	/**
	 * @return a deep clone of this KeyValuePair
	 */
	public abstract KeyValuePair<K, V> clone();
}