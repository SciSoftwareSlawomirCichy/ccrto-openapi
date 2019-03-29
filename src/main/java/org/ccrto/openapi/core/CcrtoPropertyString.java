package org.ccrto.openapi.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueObject;
import org.ccrto.openapi.core.system.SystemProperties;

/**
 * 
 * CcrtoPropertyString - przykład obiektu reprezentującego 'String'
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "String")
public class CcrtoPropertyString extends CcrtoProperty implements IValueObject {

	private static final long serialVersionUID = -461295520226385342L;

	@XmlAnyElement(lax = true)
	protected transient List<Object> any;

	/**
	 * @return the {@link #any}
	 */
	public List<Object> getAny() {
		if (any == null) {
			any = new ArrayList<>();
		}
		return any;
	}

	/**
	 * @param any
	 *            the {@link #any} to set
	 */
	public void setAny(List<Object> any) {
		this.any = any;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return StringUtils.isBlank(this.propertyValue);
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyString getInstance(String value) {
		return getInstance(/* systemName */ null, value);
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param systemName
	 *            nazwa systemu, dla którego zostanie odczytana wartość maksymalnej
	 *            długości String'a. Jeżeli wartość ta zostanie przekroczona typ
	 *            obiektu zostanie zmieniony na {@link CcrtoPropertyType#TEXT}.
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyString getInstance(String systemName, String value) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		CcrtoPropertyString instance = new CcrtoPropertyString();
		instance.propertyValue = value;
		Integer maxLength = systemProperties.getStringMaxLength();
		CcrtoPropertyType stringType = CcrtoPropertyType
				.getType(instance.type == null ? CcrtoPropertyType.STRING.getName() : instance.type);
		if (CcrtoPropertyType.STRING.equals(stringType) && value.length() > maxLength.intValue()) {
			instance.type = CcrtoPropertyType.TEXT.getName();
		}
		return instance;
	}

}
