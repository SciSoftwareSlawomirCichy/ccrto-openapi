package org.ccrto.openapi.values.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.values.DateValue;

/**
 * 
 * DateValueUtils
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class DateValueUtils {

	private DateValueUtils() {
	}

	/**
	 * Formatowanie daty - domyślnie zawsze formatujemy do daty długiej (z czasem)
	 * ze względu na to, że inne systemy mogą nie rozróżniać typu Date od DateLong.
	 * Nie możemy ryzykować utratą danych w systemie.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param calendar
	 *            obiekt kalendarza
	 * @return ta w postaci sformatowanej jako data długa (z czasem)
	 */
	public static String convertCalendarToString(Context context, Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		return convertCalendarToString(context, calendar, ContextHelper.getLongDateFormatPattern(context));
	}

	/**
	 * Formatowanie daty
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param calendar
	 *            obiekt kalendarza
	 * @param fromatPattern
	 *            maska formatowania daty
	 * @return sformatowana data
	 */
	public static String convertCalendarToString(Context context, Calendar calendar, String fromatPattern) {
		if (calendar == null) {
			return null;
		}
		Locale lLoc = ContextHelper.getUserLocale(context);
		return convertCalendarToString(calendar, fromatPattern, lLoc);
	}

	/**
	 * Formatowanie daty
	 * 
	 * @param calendar
	 *            obiekt kalendarza
	 * @param fromatPattern
	 *            maska formatowania daty
	 * @param locale
	 *            wersja językowa formatowanej daty
	 * @return sformatowana data
	 */
	public static String convertCalendarToString(Calendar calendar, String fromatPattern, Locale locale) {
		String result = null;
		if (calendar != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(fromatPattern, locale);
			result = sdf.format(calendar.getTime());
		}
		return result;
	}

	/**
	 * Parsowanie daty, format dat jest rozpoznawany poprzez długość string'a:
	 * 
	 * <code>
	  boolean isLong = date.length() > MercuryConfig.getDateFormat().length();
	 </code>
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param date
	 *            data w postaci łańcucha
	 * @return obiekt kalendarza
	 */
	public static Calendar parseDate(Context context, DateValue date) {
		if (date == null || StringUtils.isBlank(date.getString())) {
			return null;
		}
		String dateString = date.getString();
		Calendar result = null;
		if (StringUtils.isNotBlank(dateString)) {
			SimpleDateFormat sdf = prepareSimpleDateFormat(context, /* customFormatPattern */ date.getFormat(),
					/* customLocale */ date.getLocale(), dateString);
			Date time;
			try {
				time = sdf.parse(dateString);
				result = Calendar.getInstance();
				result.setTime(time);
			} catch (ParseException e) {
				throw new IllegalAccessError(e.getMessage());
			}
		}
		return result;
	}

	private static SimpleDateFormat prepareSimpleDateFormat(final Context context, final String customFormatPattern,
			final String customLocale, final String dateString) {
		Locale userLocale = getLocale(context, customLocale);
		String fromatPattern = getFormatPattern(context, customFormatPattern, userLocale, dateString);
		return new SimpleDateFormat(fromatPattern, userLocale);
	}

	private static Locale getLocale(final Context context, final String customLocale) {
		if (StringUtils.isNotBlank(customLocale)) {
			return Locale.forLanguageTag(customLocale);
		} else {
			return ContextHelper.getUserLocale(context);
		}
	}

	private static String getFormatPattern(Context context, String customFormatPattern, Locale userLocale,
			String dateString) {
		String formatPattern = customFormatPattern;
		if (StringUtils.isNotBlank(formatPattern)) {
			return formatPattern;
		}
		formatPattern = ContextHelper.getShortDateFormatPattern(context);
		boolean isShortDate = DateValueAnalyser.analyse(ContextHelper.getSystemNameInContext(context), formatPattern,
				userLocale, dateString);
		if (isShortDate) {
			return formatPattern;
		}
		return ContextHelper.getLongDateFormatPattern(context);
	}

}
