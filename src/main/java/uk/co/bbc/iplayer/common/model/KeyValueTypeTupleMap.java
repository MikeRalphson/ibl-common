package uk.co.bbc.iplayer.common.model;

import java.util.Map;

/**
 * <p>
 * This interface represents a collection (Map) of (key, value, type) tuples.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 * @see uk.co.bbc.iplayer.common.model.KeyValueTypeTuple
 * @see uk.co.bbc.iplayer.common.model.KeyValueTypeTupleList
 */
public interface KeyValueTypeTupleMap<K extends Object, V extends Object, T extends Object>
        extends Map<K, KeyValueTypeTuple<K, V, T>> {

}
