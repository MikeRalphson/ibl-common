package bbc.iplayer.ibl.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "child")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "aChildProperty", "anotherChildProperty", "yetAnotherChildProperty" })
public class SimpleChildEntity {

	private String aChildProperty;
	private String anotherChildProperty;
	private Integer yetAnotherChildProperty;

	public SimpleChildEntity() {
		super();
	}

	public SimpleChildEntity(String aChildProperty, String anotherChildProperty, Integer yetAnotherChildProperty) {
		super();
		this.aChildProperty = aChildProperty;
		this.anotherChildProperty = anotherChildProperty;
		this.yetAnotherChildProperty = yetAnotherChildProperty;
	}

	public String getaChildProperty() {
		return aChildProperty;
	}

	public void setaChildProperty(String aChildProperty) {
		this.aChildProperty = aChildProperty;
	}

	public String getAnotherChildProperty() {
		return anotherChildProperty;
	}

	public void setAnotherChildProperty(String anotherChildProperty) {
		this.anotherChildProperty = anotherChildProperty;
	}

	public Integer getYetAnotherChildProperty() {
		return yetAnotherChildProperty;
	}

	public void setYetAnotherChildProperty(Integer yetAnotherChildProperty) {
		this.yetAnotherChildProperty = yetAnotherChildProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aChildProperty == null) ? 0 : aChildProperty.hashCode());
		result = prime * result + ((anotherChildProperty == null) ? 0 : anotherChildProperty.hashCode());
		result = prime * result + ((yetAnotherChildProperty == null) ? 0 : yetAnotherChildProperty.hashCode());
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
		SimpleChildEntity other = (SimpleChildEntity) obj;
		if (aChildProperty == null) {
			if (other.aChildProperty != null)
				return false;
		}
		else if (!aChildProperty.equals(other.aChildProperty))
			return false;
		if (anotherChildProperty == null) {
			if (other.anotherChildProperty != null)
				return false;
		}
		else if (!anotherChildProperty.equals(other.anotherChildProperty))
			return false;
		if (yetAnotherChildProperty == null) {
			if (other.yetAnotherChildProperty != null)
				return false;
		}
		else if (!yetAnotherChildProperty.equals(other.yetAnotherChildProperty))
			return false;
		return true;
	}
}
