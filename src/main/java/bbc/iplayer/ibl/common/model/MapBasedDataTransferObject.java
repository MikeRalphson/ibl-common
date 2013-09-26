package bbc.iplayer.ibl.common.model;

import java.io.Serializable;

/**
 * <p>
 * 	This interface represents a map based DataTransferObject.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePairMap
 * @see <a href="http://en.wikipedia.org/wiki/Data_transfer_object">Data Transfer Object</a>
 *
 */
public interface MapBasedDataTransferObject
extends KeyValuePairMap<String, Serializable>, Serializable
{

}
