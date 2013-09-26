package bbc.iplayer.ibl.common.model;

import java.util.List;

/**
 * <p>
 *	This interface represents a collection (List) of (key, value) pairs.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePair
 * @see bbc.iplayer.ibl.common.model.KeyValuePairMap
 *
 */
public interface KeyValuePairList<K extends Object, V extends Object>
extends List<KeyValuePair<K, V>> {

}
