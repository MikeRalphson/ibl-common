package bbc.iplayer.ibl.common.model.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

import bbc.iplayer.ibl.common.model.KeyValuePairMap;

/**
 * <p>
 * 	This class provides a default implementation of the KeyValuePairMap interface
 * 	by extending the java.util.HashMap class.
 * </p>
 * <p>
 * 	Also:
 * 	<ul>
 * 		<li>key is a String</li>
 * 		<li>value is a String</li>
 * 	</ul>
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
 * @see java.util.HashMap
 */
@XmlTransient
@JsonIgnoreType
public class DefaultKeyValuePairMapImpl
extends LinkedHashMap<String, String>
implements KeyValuePairMap<String, String> {

	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * 	Default no parameters constructor that creates an empty instance of
	 * 	DefaultKeyValuePairMapImpl.
	 * </p>
	 *
	 */
	public DefaultKeyValuePairMapImpl() {
		super();
	}

	/**
	 * <p>
	 * 	Simple one argument constructor that creates an instance of
	 * 	DefaultKeyValuePairMapImpl with the elements provided.
	 * </p>
	 *
	 * @param map a Map containing the elements to be used
	 */
	public DefaultKeyValuePairMapImpl(Map<String, String> map) {
		super(map);
	}
}
