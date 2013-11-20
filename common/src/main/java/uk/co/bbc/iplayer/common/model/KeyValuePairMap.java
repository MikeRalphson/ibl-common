package uk.co.bbc.iplayer.common.model;

import java.util.Map;

/**
 * <p>
 * This interface represents a collection (Map) of (key, value) pairs.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 * @see uk.co.bbc.iplayer.common.model.KeyValuePair
 * @see uk.co.bbc.iplayer.common.model.KeyValuePairList
 */
public interface KeyValuePairMap<K extends Object, V extends Object>
        extends Map<K, V> {

}
