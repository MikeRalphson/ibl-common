package bbc.iplayer.ibl.common.model.impl;

import java.util.LinkedHashMap;
import java.util.Map;

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
import bbc.iplayer.ibl.common.model.KeyValuePairMap;

/**
 * <p>
 * 	This class provides an alternative (ordered) default implementation of the
 * 	KeyValuePairMap interface by extending the java.util.LinkedHashMap class.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 		</ul>
 * </p>
 * <p>
 * 	And for each map entry:
 * 	<ul>
 * 		<li>key is used as the key</li>
 * 		<li>value is used as the value</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePairMap
 * @see java.util.LinkedHashMap
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
public class DefaultOrderedKeyValuePairMapImpl
extends LinkedHashMap<String, String>
implements KeyValuePairMap<String, String> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * 	Default no parameters constructor that creates an empty instance of
	 * 	DefaultOrderedKeyValuePairMapImpl.
	 * </p>
	 *
	 */
	public DefaultOrderedKeyValuePairMapImpl() {
		super();
	}

	/**
	 * <p>
	 * 	Simple one argument constructor that creates an instance of
	 * 	DefaultOrderedKeyValuePairMapImpl with the elements provided.
	 * </p>
	 *
	 * @param map a Map containing the elements to be used
	 */
	public DefaultOrderedKeyValuePairMapImpl(Map<String, String> map) {
		super(map);
	}
}
