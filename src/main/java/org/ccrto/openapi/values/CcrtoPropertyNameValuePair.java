package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.values.api.IValueNameValuePair;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * CcrtoValueNameValuePair implementacja Java dla typu reprezentującego parę
 * nazwy i wartości
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoNameValuePair")
public class CcrtoPropertyNameValuePair extends CcrtoProperty implements IValueNameValuePair {

	private static final long serialVersionUID = 9184566663596306577L;
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_VALUE = "value";

	@XmlElement(required = true, name = "name")
	private String name;

	@XmlElement(required = true, name = "value")
	private String value;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(PROPERTY_NAME, name);
		jsonObject.put(PROPERTY_VALUE, value);
		return jsonObject.toString();
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
		CcrtoPropertyNameValuePair other = (CcrtoPropertyNameValuePair) obj;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}

		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public void setType(String type) {
		CcrtoPropertyType fieldType = CcrtoPropertyType.getType(type);
		if (fieldType == null || !CcrtoPropertyType.NVP.equals(fieldType)) {
			throw new IllegalArgumentException(
					String.format("Type should be value: \"%s\"", CcrtoPropertyType.NVP.getName()));
		}
		this.type = type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return false;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param name
	 *            nazwa, która ma być reprezentowana przez obiekt
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNameValuePair getInstance(String name, String value) {
		CcrtoPropertyNameValuePair instance = new CcrtoPropertyNameValuePair();
		instance.name = name;
		instance.value = value;
		return instance;
	}

}
