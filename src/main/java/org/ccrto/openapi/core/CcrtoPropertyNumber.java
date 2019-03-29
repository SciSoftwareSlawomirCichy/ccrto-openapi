package org.ccrto.openapi.core;

import java.math.BigDecimal;
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
import org.ccrto.openapi.core.internal.IValueNumber;
import org.ccrto.openapi.core.system.SystemProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Number")
public class CcrtoPropertyNumber extends CcrtoProperty implements IValueNumber {

	private static final long serialVersionUID = -4976327591500271937L;

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
		if (numberType == null || !numberType.isNumber()) {
			throw new IllegalArgumentException(
					"Type should be one of values: \"Number\", \"Decimal\", \"Double\", \"Float\", \"Long\", \"Integer\" or \"Currency\"");
		}
		this.type = numberType.getName();
	}

	@Override
	public Double toDouble(String systemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double toDouble(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal toBigDecimal(String systemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal toBigDecimal(Context context) {
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
	public static CcrtoPropertyNumber getInstance(BigInteger value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.INTEGER.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNumber getInstance(Integer value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.INTEGER.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNumber getInstance(Long value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
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
	public static CcrtoPropertyNumber getInstance(Double value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.DOUBLE.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNumber getInstance(Float value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.FLOAT.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNumber getInstance(BigDecimal value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		instance.type = CcrtoPropertyType.DECIMAL.getName();
		return instance;
	}

	/**
	 * Pobranie instancji obiektu reprezentującego podaną wartość
	 * 
	 * @param value
	 *            wartość, która ma być reprezentowana przez obiekt
	 * @return instancja obiektu
	 */
	public static CcrtoPropertyNumber getInstance(Number value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.propertyValue = value.toString();
		}
		setType(value, instance);
		return instance;
	}

	public static CcrtoPropertyNumber getInstance(String systemName, Number value, boolean forRequest) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return getInstance(context, value, forRequest);
	}

	public static CcrtoPropertyNumber getInstance(Context context, Number value, boolean forRequest) {
		DecodeMethod decodeMethod = ContextHelper.getDecodeMethod(context, forRequest);
		return getInstance(context, value, decodeMethod);
	}

	public static CcrtoPropertyNumber getInstance(Context context, Number value, DecodeMethod decodeMethod) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value == null) {
			/* zwracam pustą instancję obiektu reprezentującego cenę */
			return instance;
		}
		boolean isInteger = setType(value, instance);
		boolean isNotEncoded = (DecodeMethod.ALL_WITHOUT_LOB.equals(decodeMethod)
				|| DecodeMethod.ALL.equals(decodeMethod));
		if (isNotEncoded) {
			instance.isEncoded = isNotEncoded;
			Locale lLoc = ContextHelper.getUserLocale(context);
			String format;
			if (isInteger) {
				format = ContextHelper.getIntegerFormat(context);
			} else {
				format = ContextHelper.getNumberFormat(context);
			}
			DecimalFormat df = new DecimalFormat(format, new DecimalFormatSymbols(lLoc));
			instance.propertyValue = df.format(value);
		} else {
			instance.propertyValue = value.toString();
		}
		return instance;
	}

	private static boolean setType(Number value, CcrtoPropertyNumber instance) {
		boolean isInteger = false;
		if (value instanceof Double) {
			instance.setType(Double.class.getSimpleName());
		} else if (value instanceof BigDecimal) {
			instance.setType(CcrtoPropertyType.DECIMAL.getName());
		} else if (value instanceof Long) {
			instance.setType(Long.class.getSimpleName());
			isInteger = true;
		} else if (value instanceof Integer) {
			instance.setType(Integer.class.getSimpleName());
			isInteger = true;
		} else if (value instanceof Float) {
			instance.setType(Float.class.getSimpleName());
		} else if (value instanceof BigInteger || value instanceof Byte) {
			instance.setType(CcrtoPropertyType.INTEGER.getName());
			isInteger = true;
		}
		return isInteger;
	}

}
