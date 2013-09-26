package bbc.iplayer.ibl.common;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "parent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "aProperty", "anotherProperty", "children", "yetAnotherProperty" })
public class SimpleParentEntity {

	private String aProperty;
	private String anotherProperty;
	@XmlElementWrapper(name = "children")
	@XmlElement(name = "child")
	private List<SimpleChildEntity> children;
	private Long yetAnotherProperty;

	public SimpleParentEntity() {
		super();
	}

	public SimpleParentEntity(String aProperty, String anotherProperty, List<SimpleChildEntity> children, Long yetAnotherProperty) {
		super();
		this.aProperty = aProperty;
		this.anotherProperty = anotherProperty;
		this.children = children;
		this.yetAnotherProperty = yetAnotherProperty;
	}

	public String getaProperty() {
		return aProperty;
	}

	public void setaProperty(String aProperty) {
		this.aProperty = aProperty;
	}

	public String getAnotherProperty() {
		return anotherProperty;
	}

	public void setAnotherProperty(String anotherProperty) {
		this.anotherProperty = anotherProperty;
	}

	public List<SimpleChildEntity> getChildren() {
		return children;
	}

	public void setChildren(List<SimpleChildEntity> children) {
		this.children = children;
	}

	public Long getYetAnotherProperty() {
		return yetAnotherProperty;
	}

	public void setYetAnotherProperty(Long yetAnotherProperty) {
		this.yetAnotherProperty = yetAnotherProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aProperty == null) ? 0 : aProperty.hashCode());
		result = prime * result + ((anotherProperty == null) ? 0 : anotherProperty.hashCode());
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((yetAnotherProperty == null) ? 0 : yetAnotherProperty.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleParentEntity other = (SimpleParentEntity) obj;
		if (aProperty == null) {
			if (other.aProperty != null)
				return false;
		}
		else if (!aProperty.equals(other.aProperty))
			return false;
		if (anotherProperty == null) {
			if (other.anotherProperty != null)
				return false;
		}
		else if (!anotherProperty.equals(other.anotherProperty))
			return false;
		if (children == null) {
			if (other.children != null)
				return false;
		}
		else if (!children.equals(other.children))
			return false;
		if (yetAnotherProperty == null) {
			if (other.yetAnotherProperty != null)
				return false;
		}
		else if (!yetAnotherProperty.equals(other.yetAnotherProperty))
			return false;
		return true;
	}
}
