package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.api.INameValuePairValue;
import pro.ibpm.mercury.attrs.sub.JSONNameValuePairSubType;
import pro.ibpm.mercury.xml.NameValuePair2XMLUtils;

/**
 * 
 * JSONNameValuePair implementacja Java dla podtypu JSON_NVP.
 * 
 * @see AttributeType.JSON_NVP
 * @see JSONNameValuePairSubType
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "NameValuePair", namespace = "http://pro.ibpm.mercury.attrs")
public class NameValuePairValue implements INameValuePairValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6200242333333382359L;
	public static final String NAME_AND_VALUE_SEPARATOR = "^";

	@XmlAttribute(required = false)
	private String type;

	@XmlElement(required = true, name = "name", namespace = "http://pro.ibpm.mercury.attrs")
	private StringValue stringName;

	@XmlElement(required = true, name = "value", namespace = "http://pro.ibpm.mercury.attrs")
	private StringValue stringValue;

	@Override
	public String toXMLItem() {
		return NameValuePair2XMLUtils.toXMLItem(this).toString();
	}

	public NameValuePairValue() {
		this.type = AttributeType.NVP.getName();
	}

	public NameValuePairValue(String name, String value) {
		this();
		this.stringName = new StringValue(name);
		this.stringValue = new StringValue(value);
	}

	@Override
	public String getName() {
		return (stringName != null ? stringName.getValue() : null);
	}

	@Override
	public void setName(String name) {
		this.stringName = new StringValue(name);
	}

	@Override
	public String getValue() {
		return (stringValue != null ? stringValue.getValue() : null);
	}

	@Override
	public void setValue(String value) {
		this.stringValue = new StringValue(value);
	}

	@Override
	public String toString() {
		return new StringBuilder(getValue()).append(NAME_AND_VALUE_SEPARATOR).append(getName()).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
		result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
		NameValuePairValue other = (NameValuePairValue) obj;

		if (stringName == null) {
			if (other.stringName != null)
				return false;
		} else if (!stringName.equals(other.stringName)) {
			return false;
		}

		if (stringValue == null) {
			if (other.stringValue != null)
				return false;
		} else if (!stringValue.equals(other.stringValue)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the {@link #stringName}
	 */
	public StringValue getStringName() {
		return stringName;
	}

	/**
	 * @param stringName
	 *            the {@link #stringName} to set
	 */
	public void setStringName(StringValue stringName) {
		this.stringName = stringName;
	}

	/**
	 * @return the {@link #stringValue}
	 */
	public StringValue getStringValue() {
		return stringValue;
	}

	/**
	 * @param stringValue
	 *            the {@link #stringValue} to set
	 */
	public void setStringValue(StringValue stringValue) {
		this.stringValue = stringValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
