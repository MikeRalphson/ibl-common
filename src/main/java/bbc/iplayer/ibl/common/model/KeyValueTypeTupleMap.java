package bbc.iplayer.ibl.common.model;

import java.util.Map;

/**
 * <p>
 * 	This interface represents a collection (Map) of (key, value, type) tuples.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTuple
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTupleList
 *
 */
public interface KeyValueTypeTupleMap<K extends Object, V extends Object, T extends Object>
extends Map<K, KeyValueTypeTuple<K, V, T>> {

}
