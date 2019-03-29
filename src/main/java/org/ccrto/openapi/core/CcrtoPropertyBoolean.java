package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueBoolean;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Boolean")
public class CcrtoPropertyBoolean extends CcrtoProperty implements IValueBoolean {

	private static final long serialVersionUID = -2833142378143619965L;

	/* Overridden (non-Javadoc) */
	@Override
	public void setType(String type) {
		CcrtoPropertyType booleanType = CcrtoPropertyType.getType(type);
		if (booleanType == null || !CcrtoPropertyType.BOOLEAN.equals(booleanType)) {
			throw new IllegalArgumentException(
					String.format("Type should be value: \"%s\"", CcrtoPropertyType.BOOLEAN.getName()));
		}
		this.type = booleanType.getName();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public Boolean toBoolean() {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return StringUtils.isBlank(propertyValue);
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyBoolean getInstance(Boolean value) {
		CcrtoPropertyBoolean instance = new CcrtoPropertyBoolean();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		return instance;
	}

}
