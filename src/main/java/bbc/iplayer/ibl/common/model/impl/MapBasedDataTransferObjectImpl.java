package bbc.iplayer.ibl.common.model.impl;

import java.io.Serializable;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> IBL-7: reworked common model, added mapper between Amazon specific
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

<<<<<<< HEAD
=======
>>>>>>> Added generic KeyValuePair, KeyValueTypeTuple, KeyValueAttributesTuple
=======
>>>>>>> IBL-7: reworked common model, added mapper between Amazon specific
import bbc.iplayer.ibl.common.model.MapBasedDataTransferObject;

/**
 * <p>
 * 	This class provides a default implementation of the MapBasedDataTransferObject interface
 * 	by extending the OrderedStringSerializableKeyValuePairMapImpl<Serializable> class.
 * </p>
 * <p>
 * Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a Serializable object</li>
 * 	</ul>
 * </p>
 * <p>
 * 	And for each map entry:
 * 	<ul>
 * 		<li>key is used as the key</li>
 * 		<li>value is used as the value</li>
 * 	</ul>
 * </p>
 * <p>
 * 	Finally, all the elements of the Map are ordered by the order they were added to the Map.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.MapBasedDataTransferObject
 * @see bbc.iplayer.ibl.common.model.impl.OrderedStringSerializableKeyValuePairMapImpl
 *
 */
<<<<<<< HEAD
<<<<<<< HEAD
@XmlTransient
@JsonIgnoreType
=======
>>>>>>> Added generic KeyValuePair, KeyValueTypeTuple, KeyValueAttributesTuple
=======
@XmlTransient
@JsonIgnoreType
>>>>>>> IBL-7: reworked common model, added mapper between Amazon specific
public class MapBasedDataTransferObjectImpl
extends OrderedStringSerializableKeyValuePairMapImpl<Serializable>
implements MapBasedDataTransferObject
{
	private static final long serialVersionUID = 1L;
}
