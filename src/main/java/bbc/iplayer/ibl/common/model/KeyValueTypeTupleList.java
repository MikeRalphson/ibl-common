package bbc.iplayer.ibl.common.model;

import java.util.List;

/**
 * <p>
 * 	This interface represents a collection (List) of (key, value, type) tuples.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTuple
 * @see bbc.iplayer.ibl.common.model.KeyValueTypeTupleMap
 */
public interface KeyValueTypeTupleList<K extends Object, V extends Object, T extends Object>
extends List<KeyValueTypeTuple<K, V, T>> {

}
