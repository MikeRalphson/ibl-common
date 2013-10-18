package bbc.iplayer.ibl.common.model.impl;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

@XmlTransient
@JsonIgnoreType
public abstract class AbstractElementWrapper<T extends AbstractElement<T>>
extends HashMap<String, T>{
	
	private static final long serialVersionUID = 1L;

	private Integer totalCount;

	public AbstractElementWrapper() {
		super();
	}

	public Integer getCount() {
		return size();
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
