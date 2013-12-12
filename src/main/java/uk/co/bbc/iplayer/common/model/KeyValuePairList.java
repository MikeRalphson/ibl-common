package uk.co.bbc.iplayer.common.model;

import java.util.List;

/**
 * <p>
 * This interface represents a collection (List) of (key, value) pairs.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 * @see uk.co.bbc.iplayer.common.model.KeyValuePair
 * @see uk.co.bbc.iplayer.common.model.KeyValuePairMap
 */
public interface KeyValuePairList<K extends Object, V extends Object>
        extends List<KeyValuePair<K, V>> {

}
