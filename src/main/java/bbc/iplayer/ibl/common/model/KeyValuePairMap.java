package bbc.iplayer.ibl.common.model;

import java.util.Map;

/**
 * <p>
 * 	This interface represents a collection (Map) of (key, value) pairs.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePair
 * @see bbc.iplayer.ibl.common.model.KeyValuePairList
 */
public interface KeyValuePairMap<K extends Object, V extends Object>
extends Map<K, V> {

}
