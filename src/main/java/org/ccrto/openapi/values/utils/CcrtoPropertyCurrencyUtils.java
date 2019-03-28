package org.ccrto.openapi.values.utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.values.CcrtoPropertyCurrency;

/**
 * 
 * CcrtoPropertyCurrencyUtils klasa narzędziowa obsługująca 'Currency'
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CcrtoPropertyCurrencyUtils {

	public static final String CURRENCY_CODE_PATTERN = "([A-Z]{3})";
	public static final int UNBREAKABLE_SPACE = 160;
	public static final Pattern CODE_PATTERN = Pattern.compile(CURRENCY_CODE_PATTERN);
	public static final String CONTEXT_EXCHANGE_DATE_PROP = "CurrencyValueUtils.exhangeDate";

	private CcrtoPropertyCurrencyUtils() {
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
	public static String getCurrencyCode(Context context, CcrtoPropertyCurrency currency) {
		String defaultCode = ContextHelper.getCurrencyCode(context);
		if (currency == null || currency.isNull()) {
			return defaultCode;
		}
		String currCode = defaultCode;
		Matcher m = CODE_PATTERN.matcher(currency.getObjectValue());
		if (m.find()) {
			currCode = m.group();
		}
		return currCode;
	}

	/**
	 * Pobranie formatu liczby na podstawie kontekstu.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return format liczby
	 */
	public static String getFormatPattern(Context context) {
		return ContextHelper.getCurrencyFormat(context);
	}

	/**
	 * Parsowanie wartości obiektu currency do wartości liczbowej.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param currency
	 *            obiekt currency
	 * @return wartość liczbowa
	 * @throws ParseException
	 */
	public static Double getCurrencyValue(Context context, CcrtoPropertyCurrency currency) throws ParseException {
		if (currency == null || currency.isNull()) {
			return null;
		}
		String value = currency.getObjectValue();
		String currValue = value.replaceAll(CURRENCY_CODE_PATTERN, "");
		Locale lLoc = ContextHelper.getUserLocale(context);
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(lLoc);
		if (symbols.getGroupingSeparator() == UNBREAKABLE_SPACE && currValue.contains(" ")) {
			currValue = currValue.replaceAll("\\ ", "" + symbols.getGroupingSeparator());
		}
		String formatPattern = getFormatPattern(context);
		DecimalFormat df = new DecimalFormat(formatPattern, symbols);
		return df.parse(currValue).doubleValue();
	}

	public static CurrencyValueCodePair parseCurrency(Context context, CcrtoPropertyCurrency currency)
			throws ParseException {
		if (currency == null || currency.isNull()) {
			return null;
		}
		String code = getCurrencyCode(context, currency);
		Double value = getCurrencyValue(context, currency);
		return new CurrencyValueCodePair(value, code);
	}

	/**
	 * 
	 * CurrencyValueCodePair
	 *
	 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
	 * @version $Revision: 1.1 $
	 *
	 */
	public static class CurrencyValueCodePair implements Serializable {

		private static final long serialVersionUID = -2993823321439208383L;

		private Double value;

		private String code;

		public CurrencyValueCodePair() {
			super();
		}

		public CurrencyValueCodePair(Double value, String code) {
			super();
			this.value = value;
			this.code = code;
		}

		/**
		 * @return the {@link #value}
		 */
		public Double getValue() {
			return value;
		}

		/**
		 * @param value
		 *            the {@link #value} to set
		 */
		public void setValue(Double value) {
			this.value = value;
		}

		/**
		 * @return the {@link #code}
		 */
		public String getCode() {
			return code;
		}

		/**
		 * @param code
		 *            the {@link #code} to set
		 */
		public void setCode(String code) {
			this.code = code;
		}

	}

}
