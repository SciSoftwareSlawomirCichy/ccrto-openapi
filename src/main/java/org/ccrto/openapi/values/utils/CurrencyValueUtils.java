package org.ccrto.openapi.values.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import pro.ibpm.mercury.attrs.javax.CurrencyValue;
import pro.ibpm.mercury.config.MercuryConfig;
import pro.ibpm.mercury.context.Context;

/**
 * 
 * CurrencyValueUtils klasa narzędziowa obsługująca 'Currency'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CurrencyValueUtils {

	public static final String CURRENCY_CODE_PATTERN = "([A-Z]{3})";
	public static final int UNBREAKABLE_SPACE = 160;
	public static final Pattern CODE_PATTERN = Pattern.compile(CURRENCY_CODE_PATTERN);
	public static final String CONTEXT_EXCHANGE_DATE_PROP = "CurrencyValueUtils.exhangeDate";

	private CurrencyValueUtils() {
	}

	/**
	 * Pobranie locale użytkownika na podstawie samego obiektu currency oraz
	 * kontekstu.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param instance
	 *            instancja obiektu 'Currency'
	 * @return locale użytkownika
	 */
	public static Locale getLocale(Context context, CurrencyValue instance) {
		Locale lLoc = null;
		if (StringUtils.isNotBlank(instance.getLocale())) {
			lLoc = MercuryConfig.getUserLocale(instance.getLocale());
		} else if (context != null && StringUtils.isNotBlank(context.getLocale())) {
			lLoc = MercuryConfig.getUserLocale(context.getLocale());
		} else {
			lLoc = MercuryConfig.getSystemLocale();
		}
		return lLoc;
	}

	/**
	 * Pobranie kodu waluty na podstawie samego obiektu currency oraz kontekstu.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param instance
	 *            instancja obiektu 'Currency'
	 * @return kod waluty
	 */
	public static String getCurrencyCode(Context context, CurrencyValue currency) {
		Map<String, String> formats = context != null ? context.getFormats() : null;
		String code = currency.getValueCode();
		if (StringUtils.isBlank(currency.getValueCode())) {
			code = MercuryConfig.getCurrencyCode();
			if (formats != null && formats.get(MercuryConfig.PROP_CURRENCY_FORMAT_CODE) != null) {
				code = formats.get(MercuryConfig.PROP_CURRENCY_FORMAT_CODE);
			}
		}
		return code;
	}

	/**
	 * Pobranie formatu liczby na podstawie samego obiektu currency oraz kontekstu.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param instance
	 *            instancja obiektu 'Currency'
	 * @return format liczby
	 */
	public static String getFormatPattern(Context context, CurrencyValue currency) {
		Map<String, String> formats = context != null ? context.getFormats() : null;
		String formatPattern;
		if (StringUtils.isNotBlank(currency.getFormat())) {
			formatPattern = currency.getFormat();
		} else {
			formatPattern = MercuryConfig.getCurrencyFormat();
			if (formats != null && formats.get(MercuryConfig.PROP_CURRENCY_FORMAT) != null) {
				formatPattern = formats.get(MercuryConfig.PROP_CURRENCY_FORMAT);
			}
		}
		return formatPattern;
	}

	public static String toStringValue(Context context, CurrencyValue currency) {

		if (currency == null) {
			return null;
		}

		Locale lLoc = getLocale(context, currency);
		String formatPattern = getFormatPattern(context, currency);
		String code = getCurrencyCode(context, currency);

		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat(formatPattern, new DecimalFormatSymbols(lLoc));
		sb.append(df.format(currency.getValue()));
		sb.append(code);
		return sb.toString();
	}

	public static CurrencyValue parseCurrency(Context context, String origValue) throws ParseException {
		CurrencyValue currency = new CurrencyValue();
		setFromStringValue(context, origValue, currency);
		return currency;
	}

	public static void setFromStringValue(Context context, String origValue, CurrencyValue instance)
			throws ParseException {

		if (StringUtils.isBlank(origValue)) {
			return;
		}

		Locale lLoc = getLocale(context, instance);
		String formatPattern = getFormatPattern(context, instance);
		String defaultCode = getCurrencyCode(context, instance);

		String currCode = defaultCode;
		Matcher m = CODE_PATTERN.matcher(origValue);
		if (m.find()) {
			currCode = m.group();
		}
		String currValue = origValue.replaceAll(CURRENCY_CODE_PATTERN, "");
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(lLoc);
		if (symbols.getGroupingSeparator() == UNBREAKABLE_SPACE && currValue.contains(" ")) {
			currValue = currValue.replaceAll("\\ ", "" + symbols.getGroupingSeparator());
		}
		DecimalFormat df = new DecimalFormat(formatPattern, symbols);
		instance.setLocale(lLoc.getLanguage() + "_" + lLoc.getCountry());
		instance.setFormat(formatPattern);
		instance.setValue(df.parse(currValue).doubleValue());
		instance.setValueCode(currCode);

	}

}
