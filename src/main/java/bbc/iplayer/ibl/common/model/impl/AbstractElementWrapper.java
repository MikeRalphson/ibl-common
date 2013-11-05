package bbc.iplayer.ibl.common.model.impl;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

/**
 * <p>
 * 	This (abstract) class plays the role of a wrapper for a collection of instances of 
 * 	a subclass of <code>bbc.iplayer.ibl.common.model.impl.AbstractElement</code>.
 * </p>
 *
 * @author <a href="mailto:spsarras@cyantific.net">Stelios Psarras</a>
 *
 * @see bbc.iplayer.ibl.common.model.impl.AbstractElement
 * @see java.util.HashMap
 */
@XmlTransient
@JsonIgnoreType
public abstract class AbstractElementWrapper<T extends AbstractElement<T>>
extends HashMap<String, T>{
	
	private static final long serialVersionUID = 1L;

	// used to hold the total count of elements
	// which could be different from the count of
	// the values of the collection:
	// (think of a query returning a subset of available records)
	private Integer totalCount;

	public AbstractElementWrapper() {
		super();
		setTotalCount(0);
	}

	public Integer getCount() {
		return size();
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public final void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
