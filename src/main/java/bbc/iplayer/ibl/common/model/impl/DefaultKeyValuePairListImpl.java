package bbc.iplayer.ibl.common.model.impl;

import java.util.ArrayList;
import java.util.List;

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
import bbc.iplayer.ibl.common.model.KeyValuePair;
import bbc.iplayer.ibl.common.model.KeyValuePairList;

/**
 * <p>
 * 	This class provides a default implementation of the KeyValuePairList
 * 	interface by extending the java.util.ArrayList class.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 	</ul>
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.KeyValuePair
 * @see java.util.ArrayList
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
public class DefaultKeyValuePairListImpl
extends ArrayList<KeyValuePair<String, String>>
implements KeyValuePairList<String, String> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * 	Default no parameters constructor that creates an empty instance of
	 * 	DefaultKeyValuePairListImpl.
	 * </p>
	 *
	 */
	public DefaultKeyValuePairListImpl() {
		super();
	}

	/**
	 * <p>
	 * 	Simple one argument constructor that creates an instance of
	 * 	DefaultKeyValuePairListImpl with the elements provided.
	 * </p>
	 *
	 * @param list a List containing the elements to be used
	 */
	public DefaultKeyValuePairListImpl(List<KeyValuePair<String, String>> list) {
		super(list);
	}
}
