package bbc.iplayer.ibl.common.model;

/**
 * <p>
 * 	This interface represents a (key, value, attributes) tuple.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueList
 * @see bbc.iplayer.ibl.common.model.KeyValueMap
 */
public interface KeyValueAttributesTuple<K extends Object, V extends Object>
extends KeyValuePair<K, V> {

	/**
	 * @return the attributes of this (key, value, attributes) tuple
	 */
	public abstract KeyValuePairMap<K, V> getAttributes();

	/**
	 * @param attributes to be used for this (key, value, attributes) tuple
	 */
	public abstract void setAttributes(KeyValuePairMap<K, V> attributes);

	/**
	 * @return a deep clone of this KeyValueAttributesTuple
	 */
	public abstract KeyValueAttributesTuple<K, V> clone();
}
