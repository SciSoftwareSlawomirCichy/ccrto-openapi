package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.api.IEntryValue;
import pro.ibpm.mercury.attrs.sub.EntrySubType;

/**
 * 
 * Entry implementacja reprezentacji podtypu ENTRY przeznaczona do komunikacji z
 * systemami zewnętrznymi (obiekt DTO)
 * 
 * @see AttributeType#ENTRY
 * @see EntrySubType
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Entry", namespace = "http://pro.ibpm.mercury.attrs")
public class EntryValue implements IEntryValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8766393590988585485L;

	public static final String PROPERTY_KEY = "key";
	public static final String PROPERTY_VALUE = "value";
	public static final String COLLECTION_ITEM_NAME = "entry";

	@XmlAttribute(required = false)
	private String type;

	@XmlElement(required = true, name = "key", namespace = "http://pro.ibpm.mercury.attrs")
	private StringValue stringKey;

	@XmlElement(required = true, name = "value", namespace = "http://pro.ibpm.mercury.attrs")
	private StringValue stringValue;

	public EntryValue() {
		this.type = AttributeType.ENTRY.getName();
	}

	public EntryValue(String key) {
		this();
		this.stringKey = new StringValue(key);
	}

	public EntryValue(String key, String value) {
		this();
		this.stringKey = new StringValue(key);
		this.stringValue = new StringValue(value);
	}

	public String getKey() {
		return (stringKey != null ? stringKey.getValue() : null);
	}

	public String getValue() {
		return (stringValue != null ? stringValue.getValue() : null);
	}

	@Override
	public String setValue(String value) {
		this.stringValue = new StringValue(value);
		return this.stringValue.getValue();
	}

	/**
	 * @return the {@link #stringKey}
	 */
	public StringValue getStringKey() {
		return stringKey;
	}

	/**
	 * @param stringKey
	 *            the {@link #stringKey} to set
	 */
	public void setStringKey(StringValue stringKey) {
		this.stringKey = stringKey;
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

	@Override
	public String toString() {
		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(PROPERTY_KEY, getKey());
		jsonObject.put(PROPERTY_VALUE, getValue());
		return jsonObject.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringKey == null) ? 0 : stringKey.hashCode());
		result = prime * result + ((stringValue == null) ? 0 : stringValue.hashCode());
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
		EntryValue other = (EntryValue) obj;

		if (stringKey == null) {
			if (other.stringKey != null)
				return false;
		} else if (!stringKey.equals(other.stringKey)) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
