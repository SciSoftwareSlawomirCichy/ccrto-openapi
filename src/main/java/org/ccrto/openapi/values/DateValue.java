package org.ccrto.openapi.values;

import java.util.Calendar;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.context.DecodeMethod;
import org.ccrto.openapi.system.SystemProperties;
import org.ccrto.openapi.system.SystemPropertiesDefaults;
import org.ccrto.openapi.values.utils.DateValueUtils;

/**
 * 
 * DateInString - obiekt reprezentujący datę w postaci 'String'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class DateValue implements ObjectValue {

	private static final long serialVersionUID = -7628005674389103812L;

	@XmlAttribute(required = false)
	private String type;

	@XmlAttribute(required = false)
	private Boolean isEncoded;

	@XmlAttribute(required = false)
	private String format;

	@XmlAttribute(required = false)
	private String locale;

	@XmlValue
	private String string;

	public DateValue() {
		this.type = DateInStringType.LONG.getName();
	}

	public boolean isEncoded() {
		return isEncoded;
	}

	public void setEncoded(boolean isEncoded) {
		this.isEncoded = isEncoded;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Pobranie instancji obiektu daty
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @return obiekt daty
	 */
	public static DateValue getInstance(String systemName) {
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
	public static DateValue getInstance(String systemName, boolean isEncoded) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		Calendar now = Calendar.getInstance();
		return getInstance(context, now, (isEncoded ? DecodeMethod.NOTHING : DecodeMethod.DATE_ONLY),
				/* customFormat */ null);
	}

	/**
	 * 
	 * @param context
	 * @param forRequest
	 * @return
	 */
	public static DateValue getInstance(Context context, boolean forRequest) {
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
	public static DateValue getInstance(Context context, Calendar date, boolean forRequest) {
		Calendar now = Calendar.getInstance();
		DecodeMethod decodeMethod;
		String decodeContextMethod;
		if (forRequest) {
			decodeContextMethod = context.getDecodeRequest();
		} else {
			decodeContextMethod = context.getDecodeResult();
		}
		if (StringUtils.isNotBlank(decodeContextMethod)) {
			decodeMethod = DecodeMethod.valueOf(decodeContextMethod);
		} else {
			decodeMethod = SystemPropertiesDefaults.DECODE_RESULT_AND_REQUEST;
		}
		return getInstance(context, now, decodeMethod, /* customFormat */ null);
	}

	/**
	 * 
	 * @param context
	 * @param date
	 * @param decodeMethod
	 * @param customFormat
	 * @return
	 */
	public static DateValue getInstance(Context context, Calendar date, DecodeMethod decodeMethod,
			String customFormat) {
		DateValue instance = new DateValue();
		if (date == null) {
			/* zwracam pustą instancję obiektu reprezentującego datę */
			return instance;
		}
		boolean isEncoded = (DecodeMethod.NOTHING.equals(decodeMethod) || DecodeMethod.LOB_ONLY.equals(decodeMethod));
		if (isEncoded) {
			instance.setEncoded(isEncoded);
			instance.setString(Long.toString(date.getTimeInMillis()));
		} else {
			Locale lLoc = ContextHelper.getUserLocale(context);
			String format;
			if (StringUtils.isNotBlank(customFormat)) {
				instance.setFormat(customFormat);
				format = customFormat;
			} else {
				format = ContextHelper.getLongDateFormatPattern(context);
			}
			instance.string = DateValueUtils.convertCalendarToString(date, format, lLoc);
		}
		return instance;
	}

	public Calendar toCalendar(String systemName) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context context = systemProperties.createDefaultContext();
		if (StringUtils.isNotBlank(systemName)) {
			ContextHelper.setSystemNameInContext(context, systemName);
		}
		return DateValueUtils.parseDate(context, this);
	}

	public Calendar toCalendar(Context context) {
		return DateValueUtils.parseDate(context, this);
	}

	@Override
	public String toString() {
		return string;
	}

	public void setType(String type) {
		DateInStringType dateType = DateInStringType.getType(type);
		if (dateType == null) {
			throw new IllegalArgumentException(
					"Type should be one of values: \"Date\", \"Calendar\", \"Timestamp\" or \"Time\"");
		}
		this.type = dateType.getName();
	}

	public String getType() {
		return type;
	}

}
