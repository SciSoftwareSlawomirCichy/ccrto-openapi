package org.ccrto.openapi.core;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueCurrency;
import org.ccrto.openapi.core.system.SystemProperties;

/**
 * 
 * CcrtoPropertyCurrency obiekt reprezentujący 'Currency'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Currency")
public class CcrtoPropertyCurrency extends CcrtoProperty implements IValueCurrency {

	private static final long serialVersionUID = -1186306668877303365L;

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

	public void setType(String type) {
		CcrtoPropertyType currencyType = CcrtoPropertyType.getType(type);
		if (currencyType == null || !CcrtoPropertyType.CURRENCY.equals(currencyType)) {
			throw new IllegalArgumentException("Type should be one of values: \"Currency\"");
		}
		this.type = type;
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
	public String getCode(String systemName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCode(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return StringUtils.isBlank(this.propertyValue);
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

	public static CcrtoPropertyCurrency getInstance(String systemName, Double value, boolean forRequest) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return getInstance(context, value, forRequest);
	}

	public static CcrtoPropertyCurrency getInstance(Context context, Double value, boolean forRequest) {
		String valueCode = ContextHelper.getCurrencyCode(context);
		DecodeMethod decodeMethod = ContextHelper.getDecodeMethod(context, forRequest);
		return getInstance(context, value, valueCode, decodeMethod);
	}

	public static CcrtoPropertyCurrency getInstance(Context context, Double value, String valueCode,
			DecodeMethod decodeMethod) {
		CcrtoPropertyCurrency instance = new CcrtoPropertyCurrency();
		if (value == null) {
			/* zwracam pustą instancję obiektu reprezentującego cenę */
			return instance;
		}
		boolean isNotEncoded = (DecodeMethod.ALL_WITHOUT_LOB.equals(decodeMethod)
				|| DecodeMethod.ALL.equals(decodeMethod));
		StringBuilder sb = new StringBuilder();
		if (isNotEncoded) {
			instance.isEncoded = isNotEncoded;
			Locale lLoc = ContextHelper.getUserLocale(context);
			String format = ContextHelper.getCurrencyFormat(context);
			DecimalFormat df = new DecimalFormat(format, new DecimalFormatSymbols(lLoc));
			sb.append(df.format(value));
			sb.append(valueCode);
		} else {
			sb.append(Double.toString(value.doubleValue()));
			sb.append(valueCode);
		}
		instance.propertyValue = sb.toString();
		return instance;
	}

}
