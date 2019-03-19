package org.ccrto.openapi.system;

import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;

import org.ccrto.openapi.context.DecodeMethod;

/**
 * 
 * SystemPropertiesDefaults
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class SystemPropertiesDefaults {

	private SystemPropertiesDefaults() {
	}

	/**
	 * Separator oddzielający wersję językową od terytorium w string'owej
	 * reprezentacji lokalizacji
	 */
	static final String LOCALE_SEPARATOR = "_";

	static final String PATH_SEPARATOR = "/";

	/** Domyślny HREF użytkowników */
	static final String SYSTEM_USER_HREF = "http://localhost/users";

	/** Domyślny HREF ról */
	static final String SYSTEM_ROLE_HREF = "http://localhost/roles";

	/** Domyślny HREF źródeł */
	static final String SYSTEM_SOURCE_HREF = "http://localhost/sources";

	/** Standardowy XML'owy format daty */
	public static final String DATE_XML_FORMAT = "yyyy/MM/dd HH:mm:ss.SS z";

	/** Domyślny format daty */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/** Domyślny format daty krótkiej */
	public static final String DATE_SHORT_FORMAT = "dd-MM-yyyy";

	/** Domyślny format daty długiej */
	public static final String DATE_LONG_FORMAT = "dd-MM-yyyy HH:mm:ss";

	/** Domyślny format daty logowania zdarzeń */
	public static final String DATE_LOGGER_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

	/** Domyślny format liczby */
	public static final String NUMBER_FORMAT = "##############0.####";

	/** Domyślny format waluty */
	public static final String CURRENCY_FORMAT = "###,###,###,###,##0.00";

	/** Domyślny kod waluty */
	public static final String CURRENCY_CODE;

	/** Domyślny format liczby całkowitej */
	public static final String INTEGER_FORMAT = "##############0";

	/** Domyślne terytorium */
	public static final String TERRITORY;

	/** Domyślna wersja językowa */
	public static final String LANG;
	/**
	 * Domyślna strefa czasowa
	 * 
	 * @see TimeZone#getAvailableIDs()
	 */
	public static final String TIMEZONE;
	/**
	 * Domyślne ustawienie flagi związanej z budowaniem zapytań wyszukujących.
	 * Domyślnie pola alternatywne są ignorowane by nie zakłóciło to kompatybilności
	 * wstecz.
	 */
	public static final boolean IGNORE_ALTERNATE_FIELDS = true;
	/**
	 * Domyślna flaga pozwalająca na zignorowanie nagłówka sprawy w odpowiedzi.
	 * Dotyczy akcji warstwy biznesowej, która zwraca dane wykorzystując uniwersalny
	 * obiekt MRC
	 */
	public static final boolean IGNORE_CASE_HEADER_IN_RESPONSE = false;

	/**
	 * Domyślna forma dekodowania wartości poszczególnych pól dla żądania oraz
	 * odpowiedzi.
	 */
	public static final DecodeMethod DECODE_RESULT_AND_REQUEST = DecodeMethod.DATE_AND_LOB;

	/** Domyślna nazwa systemu */
	public static final String SYSTEM_APPLICATION_NAME;

	/** Domyślna wersja systemu */
	public static final String SYSTEM_APPLICATION_VERSION;

	/** Domyślny identyfikator użytkownika */
	public static final String SYSTEM_USER_ID;

	/** Domyślny identyfikator roli użytkownika */
	public static final String SYSTEM_ROLE_ID = "ANONYMOUS";

	/** Domyślny identyfikator roli użytkownika */
	public static final String SYSTEM_SOURCE_ID = "UNKNOWN";

	/** Domyślny komentarz do zmian w systemie */
	public static final String SYSTEM_COMMENT = "System changes.";

	/**
	 * Maksymalna domyślna liczba wyników odpowiedzi.
	 */
	public static final int MAX_QUERY_HITS = 1000;

	public static final int MAX_DEPTH = 3;

	public static final int RESPONSE_TIMEOUT = 10000000;

	private static final String SYSTEM_LOCALE_TAG;

	static {
		TimeZone tz = Calendar.getInstance().getTimeZone();
		TIMEZONE = tz.getID();
		Locale currentLocale = Locale.getDefault();
		TERRITORY = currentLocale.getCountry();
		LANG = currentLocale.getLanguage();
		Currency currency = Currency.getInstance(currentLocale);
		CURRENCY_CODE = currency.getCurrencyCode();
		SYSTEM_APPLICATION_VERSION = System.getProperty("java.runtime.version");
		SYSTEM_APPLICATION_NAME = System.getProperty("java.runtime.name");
		SYSTEM_USER_ID = System.getProperty("user.name");
		SYSTEM_LOCALE_TAG = new StringBuilder().append(LANG).append(LOCALE_SEPARATOR).append(TERRITORY).toString();
	}

	public static String getSystemLocaleTag() {
		return SYSTEM_LOCALE_TAG;
	}
}
