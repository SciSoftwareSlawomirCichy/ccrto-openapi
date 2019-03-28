package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.values.api.IValueEntry;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * CcrtoPropertyEntry implementacja reprezentacji podtypu ENTRY przeznaczona do
 * komunikacji z systemami zewnętrznymi (obiekt DTO)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoEntry")
public class CcrtoPropertyEntry extends CcrtoProperty implements IValueEntry {

	private static final long serialVersionUID = 7687549663048266631L;
	public static final String PROPERTY_KEY = "key";
	public static final String PROPERTY_VALUE = "value";

	@XmlElement(required = true, name = "key")
	private String key;

	@XmlElement(required = true, name = "value")
	private String value;

	public CcrtoPropertyEntry() {
		super();
	}

	public CcrtoPropertyEntry(String key) {
		this();
		this.key = key;
	}

	public CcrtoPropertyEntry(String key, String value) {
		this(key);
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String setValue(String value) {
		this.value = value;
		return value;
	}

	@Override
	public String toString() {
		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(PROPERTY_KEY, getKey());
		jsonObject.put(PROPERTY_VALUE, getValue());
		return jsonObject.toString();
	}

	public void setType(String type) {
		CcrtoPropertyType fieldType = CcrtoPropertyType.getType(type);
		if (fieldType == null || !CcrtoPropertyType.ENTRY.equals(fieldType)) {
			throw new IllegalArgumentException(
					String.format("Type should be value: \"%s\"", CcrtoPropertyType.ENTRY.getName()));
		}
		this.type = type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CcrtoPropertyEntry)) {
			return false;
		}
		CcrtoPropertyEntry other = (CcrtoPropertyEntry) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return false;
	}

}
