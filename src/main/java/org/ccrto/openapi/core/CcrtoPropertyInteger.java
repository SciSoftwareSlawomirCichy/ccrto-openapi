package org.ccrto.openapi.core;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueInteger;
import org.ccrto.openapi.core.system.SystemProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Integer")
public class CcrtoPropertyInteger extends CcrtoProperty implements IValueInteger {

	private static final long serialVersionUID = -2833893109010286488L;

	@XmlAttribute(required = false)
	private Boolean isEncoded;

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


	/* Overridden (non-Javadoc) */
	@Override
	public void setType(String type) {
		CcrtoPropertyType numberType = CcrtoPropertyType.getType(type);
//		if (numberType == null || !CcrtoPropertyType.INTEGER.equals(numberType)
//				|| !CcrtoPropertyType.LONG.equals(numberType)) {
//			throw new IllegalArgumentException("Type should be one of values: \"Long\" or \"Integer\"");
//		}
		this.type = numberType.getName();
	}
	

	@Override
	public Long toLong(String systemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long toLong(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger toBigInteger(String systemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger toBigInteger(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return StringUtils.isBlank(propertyValue);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String toString() {
		return super.toString();
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
			instance.propertyValue = value.toString();
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
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.LONG.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyInteger getInstance(BigInteger value) {
		CcrtoPropertyInteger instance = new CcrtoPropertyInteger();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		return instance;
	}

	public static CcrtoPropertyInteger getInstance(String systemName, Number value, boolean forRequest) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return getInstance(context, value, forRequest);
	}

	public static CcrtoPropertyInteger getInstance(Context context, Number value, boolean forRequest) {
		DecodeMethod decodeMethod = ContextHelper.getDecodeMethod(context, forRequest);
		return getInstance(context, value, decodeMethod);
	}

	public static CcrtoPropertyInteger getInstance(Context context, Number value, DecodeMethod decodeMethod) {
		CcrtoPropertyInteger instance = new CcrtoPropertyInteger();
		if (value == null) {
			/* zwracam pustą instancję obiektu reprezentującego cenę */
			return instance;
		}
		setType(value, instance);
		boolean isNotEncoded = (DecodeMethod.ALL_WITHOUT_LOB.equals(decodeMethod)
				|| DecodeMethod.ALL.equals(decodeMethod));
		if (isNotEncoded) {
			instance.isEncoded = isNotEncoded;
			Locale lLoc = ContextHelper.getUserLocale(context);
			String format = ContextHelper.getIntegerFormat(context);
			DecimalFormat df = new DecimalFormat(format, new DecimalFormatSymbols(lLoc));
			instance.propertyValue = df.format(value);
		} else {
			instance.propertyValue = value.toString();
		}
		return instance;
	}

	private static void setType(Number value, CcrtoPropertyInteger instance) {
		if (value instanceof Long) {
			instance.setType(Long.class.getSimpleName());
		} else if (value instanceof Integer) {
			instance.setType(Integer.class.getSimpleName());
		} else if (value instanceof BigInteger || value instanceof Byte) {
			instance.setType(CcrtoPropertyType.INTEGER.getName());
		} else {
			throw new IllegalArgumentException(
					"Number should be one of types: \"Long\",  \"BigInteger\",  \"Byte\" or \"Integer\"");
		}
	}

}
