package bbc.iplayer.ibl.common.model.impl;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnoreType;

@XmlTransient
@JsonIgnoreType
public class StringSerializableItemWrapperImpl<T extends DefaultKeyValueAttributesTupleImpl>
extends HashMap<String, T>{
	
	private static final long serialVersionUID = 1L;

	//private StringSerializableKeyValuePairMapImpl<T> internalMap;
	private Integer totalCount;

	public StringSerializableItemWrapperImpl() {
		super();
		//internalMap = new StringSerializableKeyValuePairMapImpl<T>();
	}

//	public boolean isEmpty() {
//		return internalMap.isEmpty();
//	}
//
//	public T get(String key) {
//		return internalMap.get(key);
//	}
//
//	public void put(String key, T value) {
//		internalMap.put(key, value);
//	}
//
//	public void putAll(Map<String, T> values) {
//		internalMap.putAll(values);
//	}
//
//	public Collection<T> values() {
//		return internalMap.values();
//	}
	
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
