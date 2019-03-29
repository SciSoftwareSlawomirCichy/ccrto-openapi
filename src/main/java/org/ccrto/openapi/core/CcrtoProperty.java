package org.ccrto.openapi.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ CcrtoPropertyBoolean.class, CcrtoPropertyCase.class, CcrtoPropertyCurrency.class, CcrtoPropertyDate.class,
		CcrtoPropertyEntry.class, CcrtoPropertyInteger.class, CcrtoPropertyList.class, CcrtoPropertyLob.class,
		CcrtoPropertyMap.class, CcrtoPropertyNameValuePair.class, CcrtoPropertyNumber.class, CcrtoPropertyString.class,
		CcrtoPropertyURL.class, })
public class CcrtoProperty implements Serializable {

	private static final long serialVersionUID = 8993234334375963839L;

	@XmlAttribute(name = "type", required = false)
	@JsonIgnore
	protected String type;

	@JsonIgnore
	@XmlTransient
	protected String propertyName;

	@JsonIgnore
	protected String propertyValue;

	@JsonIgnore
	@XmlTransient
	protected String origType;

	@JsonIgnore
	@XmlAnyAttribute
	private Map<QName, String> otherAttributes = new HashMap<>();

	/**
	 * Gets a map that contains attributes that aren't bound to any typed property
	 * on this class.
	 * 
	 * <p>
	 * the map is keyed by the name of the attribute and the value is the string
	 * value of the attribute.
	 * 
	 * the map returned by this method is live, and you can add new attribute by
	 * updating the map directly. Because of this design, there's no setter.
	 * </p>
	 * 
	 * @return always non-null
	 */
	public Map<QName, String> getOtherAttributes() {
		return otherAttributes;
	}

	/**
	 * @return the {@link #type}
	 */
	public String getType() {
		return (type == null ? origType : type);
	}

	/**
	 * @param type
	 *            the {@link #type} to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the {@link #propertyName}
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName
	 *            the {@link #propertyName} to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Metoda pobierania wartości parametru w postaci String'a
	 * 
	 * @return wartość parametru w postaci String'a
	 */
	@XmlValue
	public String getPropertyValue() {
		return propertyValue;
	}

	/**
	 * Metoda ustawiania wartości parametru w postaci String'a
	 * 
	 * @param value
	 *            wartość parametru
	 */
	public void setPropertyValue(String value) {
		this.propertyValue = value;

	}

	final void setTypeAsNull() {
		this.origType = this.type;
		this.type = null;
	}

	final void resetType() {
		this.type = this.origType;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result + ((this.propertyValue == null) ? 0 : this.propertyValue.hashCode());
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
		if (!(obj instanceof CcrtoProperty)) {
			return false;
		}
		CcrtoProperty other = (CcrtoProperty) obj;
		if (getType() == null) {
			if (other.getType() != null) {
				return false;
			}
		} else if (!getType().equals(other.getType())) {
			return false;
		}
		if (this.propertyValue == null) {
			if (other.propertyValue != null) {
				return false;
			}
		} else if (!propertyValue.equals(other.propertyValue)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return this.propertyValue;
	}

}
