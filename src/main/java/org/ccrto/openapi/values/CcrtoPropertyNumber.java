package org.ccrto.openapi.values;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.context.DecodeMethod;
import org.ccrto.openapi.system.SystemProperties;
import org.ccrto.openapi.values.api.IValueNumber;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoNumber")
public class CcrtoPropertyNumber extends CcrtoProperty implements IValueNumber {

	private static final long serialVersionUID = -4976327591500271937L;

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
		if (numberType == null || !numberType.isNumber()) {
			throw new IllegalArgumentException(
					"Type should be one of values: \"Number\", \"Decimal\", \"Double\", \"Float\", \"Long\", \"Integer\" or \"Currency\"");
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
	public static CcrtoPropertyNumber getInstance(Integer value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.stringValue = value.toString();
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
			instance.stringValue = value.toString();
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
			instance.stringValue = value.toString();
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
			instance.stringValue = value.toString();
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
	public static CcrtoPropertyNumber getInstance(Number value) {
		CcrtoPropertyNumber instance = new CcrtoPropertyNumber();
		if (value != null) {
			instance.stringValue = value.toString();
		}
		if (value instanceof Integer) {
			instance.setType(Integer.class.getSimpleName());
		} else if (value instanceof Long) {
			instance.setType(Long.class.getSimpleName());
		} else if (value instanceof Double) {
			instance.setType(Double.class.getSimpleName());
		} else if (value instanceof Float) {
			instance.setType(Float.class.getSimpleName());
		}
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
		boolean isInteger = false;
		if (value instanceof Integer) {
			instance.setType(Integer.class.getSimpleName());
			isInteger = true;
		} else if (value instanceof Long) {
			instance.setType(Long.class.getSimpleName());
			isInteger = true;
		} else if (value instanceof Double) {
			instance.setType(Double.class.getSimpleName());
		} else if (value instanceof Float) {
			instance.setType(Float.class.getSimpleName());
		}
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
			instance.stringValue = df.format(value);
		} else {
			instance.stringValue = value.toString();
		}

		return instance;
	}

}
