package org.ccrto.openapi.values;

import java.util.Calendar;
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
import org.ccrto.openapi.values.api.IValueObject;
import org.ccrto.openapi.values.utils.CcrtoPropertyDateUtils;

/**
 * 
 * CcrtoPropertyDate - obiekt reprezentujący datę w postaci 'String'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoDate")
public class CcrtoPropertyDate extends CcrtoProperty implements IValueObject {

	private static final long serialVersionUID = -7628005674389103812L;

	@XmlAttribute(required = false)
	private Boolean isEncoded;

	private String stringValue;

	public CcrtoPropertyDate() {
		this.type = CcrtoPropertyType.DATE_LONG.getName();
	}

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
		CcrtoPropertyType dateType = CcrtoPropertyType.getType(type);
		if (dateType == null || !dateType.isDate()) {
			throw new IllegalArgumentException(
					"Type should be one of values: \"Date\", \"DateTime\", \"Timestamp\" or \"Time\"");
		}
		this.type = dateType.getName();
	}

	public Calendar toCalendar(String systemName) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return CcrtoPropertyDateUtils.parseDate(context, this);
	}

	public Calendar toCalendar(Context context) {
		return CcrtoPropertyDateUtils.parseDate(context, this);
	}

	@Override
	public String toString() {
		return (String) stringValue;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stringValue == null) ? 0 : stringValue.hashCode());
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
		if (!(obj instanceof CcrtoPropertyDate)) {
			return false;
		}
		CcrtoPropertyDate other = (CcrtoPropertyDate) obj;
		if (stringValue == null) {
			if (other.stringValue != null) {
				return false;
			}
		} else if (!stringValue.equals(other.stringValue)) {
			return false;
		}
		return true;
	}

	/* Overridden (non-Javadoc) */
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

	/**
	 * Pobranie instancji obiektu daty
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @return obiekt daty
	 */
	public static CcrtoPropertyDate getInstance(String systemName) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return getInstance(context, /* isEncoded */ false);
	}

	/**
	 * Pobranie instancji obiektu daty
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @param isEncoded
	 *            czy wartość daty ma być zakodowana w postaci liczby milisekund?
	 * @return obiekt daty
	 */
	public static CcrtoPropertyDate getInstance(String systemName, boolean isEncoded) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		Calendar now = Calendar.getInstance();
		return getInstance(context, now, (isEncoded ? DecodeMethod.NOTHING : DecodeMethod.DATE_ONLY));
	}

	/**
	 * 
	 * @param context
	 * @param forRequest
	 * @return
	 */
	public static CcrtoPropertyDate getInstance(Context context, boolean forRequest) {
		Calendar now = Calendar.getInstance();
		return getInstance(context, now, forRequest);
	}

	/**
	 * 
	 * @param context
	 * @param date
	 * @param forRequest
	 * @return
	 */
	public static CcrtoPropertyDate getInstance(Context context, Calendar date, boolean forRequest) {
		Calendar now = Calendar.getInstance();
		DecodeMethod decodeMethod = ContextHelper.getDecodeMethod(context, forRequest);
		return getInstance(context, now, decodeMethod);
	}

	/**
	 * 
	 * @param context
	 * @param date
	 * @param decodeMethod
	 * @return
	 */
	public static CcrtoPropertyDate getInstance(Context context, Calendar date, DecodeMethod decodeMethod) {
		CcrtoPropertyDate instance = new CcrtoPropertyDate();
		if (date == null) {
			/* zwracam pustą instancję obiektu reprezentującego datę */
			return instance;
		}
		boolean isEncoded = (DecodeMethod.NOTHING.equals(decodeMethod) || DecodeMethod.LOB_ONLY.equals(decodeMethod));
		if (isEncoded) {
			instance.isEncoded = isEncoded;
			instance.stringValue = Long.toString(date.getTimeInMillis());
		} else {
			Locale lLoc = ContextHelper.getUserLocale(context);
			String format = ContextHelper.getLongDateFormatPattern(context);
			instance.stringValue = CcrtoPropertyDateUtils.convertCalendarToString(date, format, lLoc);
		}
		return instance;
	}

}
