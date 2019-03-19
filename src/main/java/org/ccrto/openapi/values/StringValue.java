package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.api.IObjectValue;

/**
 * 
 * StringValue - przykład obiektu reprezentującego 'String'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "String", namespace = "http://pro.ibpm.mercury.attrs")
public class StringValue implements IObjectValue {

	private static final long serialVersionUID = 2882755747493360955L;

	@XmlAttribute(required = false)
	private String type;

	private String value;

	public StringValue() {
		this.type = AttributeType.STRING.getName();
	}

	public StringValue(String value) {
		this();
		this.value = value;
	}

	@XmlValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		StringValue other = (StringValue) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
