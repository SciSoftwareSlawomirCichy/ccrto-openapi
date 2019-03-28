package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.values.api.IValueInteger;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoInteger")
public class CcrtoPropertyInteger extends CcrtoProperty implements IValueInteger {

	private static final long serialVersionUID = -2833893109010286488L;

	@XmlAttribute(required = false)
	private Boolean isEncoded;

	private String stringValue;

	/**
	 * @return the {@link #isEncoded}
	 */
	public Boolean getIsEncoded() {
		return isEncoded;
	}

	/**
	 * @param isEncoded
	 *            the {@link #isEncoded} to set
	 */
	public void setIsEncoded(Boolean isEncoded) {
		this.isEncoded = isEncoded;
	}

	@Override
	public boolean isNull() {
		return StringUtils.isBlank(stringValue);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String getObjectValue() {
		return this.stringValue;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setObjectValue(String value) {
		this.stringValue = (String) value;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setType(String type) {
		CcrtoPropertyType numberType = CcrtoPropertyType.getType(type);
		if (numberType == null || !CcrtoPropertyType.INTEGER.equals(numberType)
				|| !CcrtoPropertyType.LONG.equals(numberType)) {
			throw new IllegalArgumentException("Type should be one of values: \"Long\" or \"Integer\"");
		}
		this.type = numberType.getName();
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyInteger getInstance(Integer value) {
		CcrtoPropertyInteger instance = new CcrtoPropertyInteger();
		if (value != null) {
			instance.stringValue = value.toString();
		}
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyInteger getInstance(Long value) {
		CcrtoPropertyInteger instance = new CcrtoPropertyInteger();
		if (value != null) {
			instance.stringValue = value.toString();
		}
		instance.type = CcrtoPropertyType.LONG.getName();
		return instance;
	}

}
