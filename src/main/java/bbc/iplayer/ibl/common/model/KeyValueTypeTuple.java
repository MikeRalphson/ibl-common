package bbc.iplayer.ibl.common.model;

/**
 * <p>
 * 	This interface represents a (key, value, type) tuple where the key, the value
 * 	and the type are all represented as objects.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTupleList
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTupleMap
 *
 */
public interface KeyValueTypeTuple<K extends Object, V extends Object, T extends Object>
extends KeyValuePair<K, V> {

	/**
	 * @return the type of this (key, value, type) tuple
	 */
	public abstract T getType();

	/**
	 * @param type to be used for this (key, value, type) tuple
	 */
	public abstract void setType(T type);

	/**
	 * @return a deep clone of this KeyValueTypeTuple
	 */
	public abstract KeyValueTypeTuple<K, V, T> clone();
}
