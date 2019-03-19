package org.ccrto.openapi.values;

import java.text.ParseException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.StringUtils;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.CurrencyValueUtils;
import pro.ibpm.mercury.attrs.javax.api.IObjectValue;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;

/**
 * 
 * CurrencyValue obiekt reprezentujący 'Currency'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://pro.ibpm.mercury.attrs")
public class CurrencyValue implements IObjectValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157368878442488372L;

	@XmlAttribute(required = false)
	private String type;

	@XmlAttribute(required = false)
	private boolean isEncoded;

	@XmlAttribute(required = false)
	private String locale;

	@XmlAttribute(required = false)
	private String format;

	private Double value;

	private String valueCode;

	public CurrencyValue() {
		this.type = AttributeType.CURRENCY.getName();
		this.isEncoded = false;
	}

	public CurrencyValue(Double value) {
		this.type = AttributeType.CURRENCY.getName();
		this.isEncoded = false;
		this.value = value;
	}

	public CurrencyValue(Double value, String valueCode) {
		this.type = AttributeType.CURRENCY.getName();
		this.isEncoded = false;
		this.value = value;
		this.valueCode = valueCode;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getValueCode() {
		return valueCode;
	}

	public void setValueCode(String valueCode) {
		this.valueCode = valueCode;
	}

	public boolean isEncoded() {
		return isEncoded;
	}

	public void setEncoded(boolean isEncoded) {
		/*
		 * nie można zmieniać, ale metoda musi być ze względu na
		 * serializację/deserializację obiektu.
		 */
		this.isEncoded = false;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@XmlValue
	public String getString() {
		Context context = MercuryConfig.createDefaultContext();
		return CurrencyValueUtils.toStringValue(context, this);
	}

	public void setString(String origValue) {
		Context context = MercuryConfig.createDefaultContext();
		try {
			CurrencyValueUtils.setFromStringValue(context, origValue, this);
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}

	public static CurrencyValue getInstance(Double value) {
		return getInstance(value, /* valueCode */ MercuryConfig.getCurrencyCode());
	}

	public static CurrencyValue getInstance(Double value, String valueCode) {
		return new CurrencyValue(value, valueCode);
	}

	public void setType(String type) {
		/* ignorujemy zmiany typu */
	}

	public String getType() {
		return type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"value\":");
		builder.append(value);
		if (StringUtils.isNotBlank(valueCode)) {
			builder.append(", \"valueCode\":\"");
			builder.append(valueCode).append("\"");
		}
		builder.append("}");
		return builder.toString();
	}
}
