package org.ccrto.openapi.core.system;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.Context;
import org.ccrto.openapi.core.ContextHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * SystemProperties
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class SystemProperties {

	protected static final Logger logger = LoggerFactory.getLogger(SystemProperties.class);

	/** Nazwa parametru definiującego domyślny format daty */
	public static final String DATE_DEFAULT_FORMAT = "system.date.format.default";

	/** Nazwa parametru definiującego format daty krótkiej */
	public static final String DATE_SHORT_FORMAT = "system.date.format.short";

	/** Nazwa parametru definiującego format daty długiej */
	public static final String DATE_LONG_FORMAT = "system.date.format.long";

	/** Nazwa parametru definiującego format daty logger'a */
	public static final String DATE_LOGGER_FORMAT = "system.date.format.logger";

	/** Nazwa parametru definiującego format dla liczb zmiennoprzecinkowych */
	public static final String NUMBER_FORMAT = "system.number.format";

	/** Nazwa parametru definiującego format dla liczb całkowitych */
	public static final String INTEGER_FORMAT = "system.integer.format";

	/**
	 * Nazwa parametru definiującego maksymalną możliwa długość typu String
	 * przechowywana w parametrach
	 */
	public static final String MAX_STRING_VALUE_LENGTH = "system.string.maxLength";

	/**
	 * Nazwa parametru definiującego maksymalną możliwa długość typu Text
	 * przechowywana w parametrach
	 */
	public static final String MAX_TEXT_VALUE_LENGTH = "system.text.maxLength";

	/**
	 * Nazwa parametru definiującego format dla liczb reprezentujących wartość
	 * pieniężną
	 */
	public static final String CURRENCY_FORMAT = "system.currency.format";

	public static final String CURRENCY_CODE = "system.currency.code";

	/** Parametr z ustawieniem domyślnego terytorium */
	public static final String SYSTEM_TERRITORY = "system.territory";

	/** Parametr z ustawieniem domyślnej wersji językowej */
	public static final String SYSTEM_LANG = "system.lang";

	/** Parametr z ustawieniem domyślnego terytorium */
	public static final String SYSTEM_TIMEZONE = "system.timezone";

	/** Parametr z ustawieniem maksymalnego rozmiaru wyniku */
	public static final String SYSTEM_MAX_QUERY_HITS = "system.maxQueryHits";

	/** Parametr z ustawieniem nazwy systemu */
	public static final String SYSTEM_APPLICATION_NAME = "system.application.name";

	/** Parametr z ustawieniem wersji systemu */
	public static final String SYSTEM_APPLICATION_VERSION = "system.application.version";

	/** Parametr z domyślnym identyfikatorem/loginem użytkownika systemowego */
	public static final String SYSTEM_USER_ID = "system.user.id";

	/** Parametr definiujący HREF użytkownika systemu */
	public static final String SYSTEM_USER_HREF = "system.user.href";

	/** Parametr z domyślnym identyfikatorem/nazwą roli systemowej */
	public static final String SYSTEM_ROLE_ID = "system.role.id";

	/** Parametr z domyślnym identyfikatorem/nazwą roli systemowej */
	public static final String SYSTEM_ROLE_HREF = "system.role.href";

	/**
	 * Parametr z domyślnym identyfikatorem/loginem źródła danych reprezentowanego
	 * przez system
	 */
	public static final String SYSTEM_SOURCE_ID = "system.source.id";

	/** Parametr definiujący HREF źródła danych reprezentowanego przez system */
	public static final String SYSTEM_SOURCE_HREF = "system.source.href";

	/** Parametr z domyślnym timeout odpowiedzi */
	public static final String SYSTEM_RESPONSE_TIMEOUT = "system.response.timeout";

	/**
	 * Dodatkowy parametr dodawany do kontekstu identyfikujący przeprowadzaną w
	 * danym momencie transakcję na encji.
	 */
	public static final String ADDITIONAL_PARAM_TRANSACTION_INFO = "context.transaction.id";
	/**
	 * Przykład zapisu sprawy pokazał, że trzeba zmieniać parametr maxResults w
	 * zależności od operacji jaka jest wykonywana. Zmiana maxResults wymaga
	 * przechowywania oryginalnej wartości, która przechowywana jest jako dodatkowy
	 * parametr kontekstu.
	 * 
	 * @see #changeMaxResults(Context, Integer)
	 * @see #returnMaxResults(Context)
	 */
	public static final String ADDITIONAL_PARAM_ORIG_MAX_RESULT = "context.orig.maxQueryHits";

	public static final String ADDITIONAL_PARAM_SYSTEM_NAME = "context.system.name";

	private final Map<String, String> properties = new HashMap<>();

	private static final Map<String, SystemProperties> app2SystemProperties = new HashMap<>();
	private static SystemProperties defaultSystemProperties = new SystemProperties();
	private static final Object app2SystemPropertiesLock = new Object();

	private String systemLocaleTag;

	private SystemProperties() {

	}

	/**
	 * Ustawianie parametrów systemu
	 * 
	 * @param systemProperties
	 *            mapa parametrów
	 */
	public void setProperties(Map<String, String> systemProperties) {
		properties.putAll(systemProperties);
	}

	/**
	 * Pobranie parametrów systemu
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @return obiekt z parametrami systemowymi zdefiniowanymi dla systemu o podanej
	 *         nazwie.
	 */
	public static SystemProperties getSystemProperties(String systemName) {
		if (StringUtils.isBlank(systemName)) {
			return getSystemProperties();
		}
		synchronized (app2SystemPropertiesLock) {
			SystemProperties systemProperties = app2SystemProperties.get(systemName);
			if (systemProperties == null) {
				systemProperties = new SystemProperties();
				app2SystemProperties.put(systemName, systemProperties);
			}
			return systemProperties;
		}
	}

	/**
	 * Pobranie parametrów systemu
	 * 
	 * @return obiekt z domyślnymi parametrami systemowymi.
	 */
	public static SystemProperties getSystemProperties() {
		return defaultSystemProperties;
	}

	/**
	 * @return ograniczenie na liczbę zwracanych w zapytaniu wierszy
	 */
	public Integer getQueryMaxHits() {
		String maxHits = properties.get(SYSTEM_MAX_QUERY_HITS);
		if (StringUtils.isNotBlank(maxHits)) {
			try {
				return Integer.parseInt(maxHits);
			} catch (Exception e) {
				logger.warn(String.format("Wrong value of property %s=%s ", SYSTEM_MAX_QUERY_HITS, maxHits), e);
			}
		}
		return SystemPropertiesDefaults.MAX_QUERY_HITS;
	}

	/**
	 * @return nazwa systemu/nazwa aplikacji
	 */
	public String getApplicationName() {
		String appName = properties.get(SYSTEM_APPLICATION_NAME);
		if (StringUtils.isNotBlank(appName)) {
			return appName;
		}
		return SystemPropertiesDefaults.SYSTEM_APPLICATION_NAME;
	}

	/**
	 * @return wersja systemu/wersja aplikacji
	 */
	public String getApplicationVersion() {
		String appName = properties.get(SYSTEM_APPLICATION_VERSION);
		if (StringUtils.isNotBlank(appName)) {
			return appName;
		}
		return SystemPropertiesDefaults.SYSTEM_APPLICATION_VERSION;
	}

	/**
	 * @return identyfikator użytkownika systemowego
	 */
	public String getSystemUserId() {
		String userId = properties.get(SYSTEM_USER_ID);
		if (StringUtils.isNotBlank(userId)) {
			return userId;
		}
		return SystemPropertiesDefaults.SYSTEM_USER_ID;
	}

	/**
	 * @return identyfikator/nazwa roli użytkownika systemowego
	 */
	public String getSystemRoleId() {
		String roleId = properties.get(SYSTEM_ROLE_ID);
		if (StringUtils.isNotBlank(roleId)) {
			return roleId;
		}
		return SystemPropertiesDefaults.SYSTEM_ROLE_ID;
	}

	/**
	 * @return identyfikator/nazwa źródła reprezentowanego przez system
	 */
	public String getSystemSourceId() {
		String sourceId = properties.get(SYSTEM_SOURCE_ID);
		if (StringUtils.isNotBlank(sourceId)) {
			return sourceId;
		}
		return SystemPropertiesDefaults.SYSTEM_SOURCE_ID;
	}

	/**
	 * Pobieranie domyślnego formatu daty.
	 * 
	 * @return domyślny format daty w systemie
	 */
	public String getDateDefaultFormat() {
		String value = properties.get(DATE_DEFAULT_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.DATE_FORMAT;
	}

	/**
	 * Pobieranie formatu daty krótkiej.
	 * 
	 * @return domyślny format daty krótkiej w systemie
	 */
	public String getDateShortFormat() {
		String value = properties.get(DATE_SHORT_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.DATE_SHORT_FORMAT;
	}

	/**
	 * Pobieranie formatu daty długiej.
	 * 
	 * @return format daty dla daty długiej (data + godzina)
	 */
	public String getDateLongFormat() {
		String value = properties.get(DATE_LONG_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.DATE_LONG_FORMAT;
	}

	/**
	 * Pobieranie formatu daty długiej dla mechanizmów logowania zdarzeń
	 * występujących w systemie.
	 * 
	 * @return format daty dla zdarzeń systemowych (data + godzina z dokładnością do
	 *         milisekund)
	 */
	public String getDateLoggerFormat() {
		String value = properties.get(DATE_LOGGER_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.DATE_LOGGER_FORMAT;
	}

	/**
	 * Pobranie formatu liczby zmiennoprzecinkowej.
	 * 
	 * @return systemowy format liczby zmiennoprzecinkowej
	 */
	public String getNumberFormat() {
		String value = properties.get(NUMBER_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.NUMBER_FORMAT;
	}

	/**
	 * Pobranie formatu liczby z wartością (ceną).
	 * 
	 * @return systemowy format liczby z wartością (ceną)
	 */
	public String getCurrencyFormat() {
		String value = properties.get(CURRENCY_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.CURRENCY_FORMAT;
	}

	/**
	 * Pobranie systemowego kodu waluty dla ceny.
	 * 
	 * @return systemowy kod waluty.
	 */
	public String getCurrencyCode() {
		String value = properties.get(CURRENCY_CODE);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.CURRENCY_CODE;
	}

	/**
	 * Pobranie formatu liczby całkowitej.
	 * 
	 * @return format liczby całkowitej
	 */
	public String getIntegerFormat() {
		String value = properties.get(INTEGER_FORMAT);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.INTEGER_FORMAT;
	}

	/**
	 * Pobranie wersji językowej dla systemu.
	 * 
	 * @return dwuliterowy kod wersji językowej np. 'pl', 'en'
	 */
	public String getSystemLang() {
		String value = properties.get(SYSTEM_LANG);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.LANG;

	}

	/**
	 * Pobranie terytorium systemu..
	 * 
	 * @return dwuliterowy kod kraju np. 'PL', 'UE'
	 */
	public String getSystemTerritory() {
		String value = properties.get(SYSTEM_TERRITORY);
		if (StringUtils.isNotBlank(value)) {
			return value;
		}
		return SystemPropertiesDefaults.TERRITORY;
	}

	/**
	 * Pobranie wersji językowej dla systemu (język + kraj).
	 * 
	 * @return kod reprezentujący wersje językową i kraj dla systemu (Locale ID)
	 */
	public String getSystemLocaleTag() {
		String lSystemLocaleTag = new StringBuilder().append(getSystemLang())
				.append(SystemPropertiesDefaults.LOCALE_SEPARATOR).append(getSystemTerritory()).toString();
		if (!lSystemLocaleTag.equals(this.systemLocaleTag)) {
			this.systemLocaleTag = lSystemLocaleTag;
		}
		return this.systemLocaleTag;
	}

	/**
	 * Pobranie obiektu {@link Locale} systemu.
	 * 
	 * @return A Locale object represents a specific geographical, political, or
	 *         cultural region. An operation that requires a Locale to perform its
	 *         task is called locale-sensitive and uses the Locale to tailor
	 *         information for the user. For example, displaying a number is a
	 *         locale-sensitive operation— the number should be formatted according
	 *         to the customs and conventions of the user's native country, region,
	 *         or culture.
	 */
	public Locale getSystemLocale() {
		return Locale.forLanguageTag(getSystemLocaleTag());
	}

	public static Locale getUserLocale(String systemName, String userLocale) {
		if (StringUtils.isNotBlank(userLocale)) {
			return Locale.forLanguageTag(userLocale);
		} else {
			SystemProperties systemProperties = getSystemProperties(systemName);
			return systemProperties.getSystemLocale();
		}
	}

	/**
	 * Pobranie strefy czasowej systemu.
	 * 
	 * @return obiekt strefy czasowej systemu
	 */
	public TimeZone getSystemTimeZone() {
		String timeZone = getSystemTimeZoneID();
		return TimeZone.getTimeZone(timeZone);
	}

	/**
	 * Pobranie identyfikatora strefy czasowej systemu
	 * 
	 * @return identyfikator strefy czasowej systemu
	 */
	public String getSystemTimeZoneID() {
		String timeZone = properties.get(SYSTEM_TIMEZONE);
		if (StringUtils.isBlank(timeZone)) {
			timeZone = SystemPropertiesDefaults.TIMEZONE;
		}
		return timeZone;
	}

	public static TimeZone getUserTimeZone(String systemName, String userTimeZone) {
		if (StringUtils.isNotBlank(userTimeZone)) {
			return TimeZone.getTimeZone(userTimeZone);
		} else {
			SystemProperties systemProperties = getSystemProperties(systemName);
			return systemProperties.getSystemTimeZone();
		}
	}

	/**
	 * @return domyślny timeout odpowiedzi, jeżeli odpowiedni parametr
	 *         {@link #SYSTEM_RESPONSE_TIMEOUT} nie jest ustawiony zwraca
	 *         {@link Integer#MAX_VALUE}.
	 */
	public Integer getResponseTimeout() {
		String timeout = properties.get(SYSTEM_RESPONSE_TIMEOUT);
		if (StringUtils.isNotBlank(timeout)) {
			try {
				return Integer.parseInt(timeout);
			} catch (Exception e) {
				logger.warn(String.format("Wrong value of property %s=%s ", SYSTEM_RESPONSE_TIMEOUT, timeout), e);
			}
		}
		return SystemPropertiesDefaults.RESPONSE_TIMEOUT;
	}

	/**
	 * Pobieranie HREF dla użytkownika
	 * 
	 * @param userId
	 *            identyfikator użytkownika
	 * @return utworzony na podstawie danych systemowych HREF dla użytkownika o
	 *         podanym identyfikatorze
	 */
	public String getUserHref(String userId) {
		String baseHref = properties.get(SYSTEM_USER_HREF);
		if (StringUtils.isBlank(baseHref)) {
			baseHref = SystemPropertiesDefaults.SYSTEM_USER_HREF;
		}
		if (!baseHref.endsWith(SystemPropertiesDefaults.PATH_SEPARATOR)) {
			baseHref = baseHref.concat(SystemPropertiesDefaults.PATH_SEPARATOR);
		}
		return baseHref.concat(userId);
	}

	/**
	 * Pobieranie HREF dla roli
	 * 
	 * @param roleId
	 *            identyfikator roli
	 * @return utworzony na podstawie danych systemowych HREF dla roli o podanym
	 *         identyfikatorze
	 */
	public String getRoleHref(String roleId) {
		String baseHref = properties.get(SYSTEM_ROLE_HREF);
		if (StringUtils.isBlank(baseHref)) {
			baseHref = SystemPropertiesDefaults.SYSTEM_ROLE_HREF;
		}
		if (!baseHref.endsWith(SystemPropertiesDefaults.PATH_SEPARATOR)) {
			baseHref = baseHref.concat(SystemPropertiesDefaults.PATH_SEPARATOR);
		}
		return baseHref.concat(roleId);
	}

	/**
	 * Pobieranie HREF dla źródła
	 * 
	 * @param userId
	 *            identyfikator źródła
	 * @return utworzony na podstawie danych systemowych HREF dla źródła o podanym
	 *         identyfikatorze
	 */
	public String getSourceHref(String sourceId) {
		String baseHref = properties.get(SYSTEM_SOURCE_HREF);
		if (StringUtils.isBlank(baseHref)) {
			baseHref = SystemPropertiesDefaults.SYSTEM_SOURCE_HREF;
		}
		if (!baseHref.endsWith(SystemPropertiesDefaults.PATH_SEPARATOR)) {
			baseHref = baseHref.concat(SystemPropertiesDefaults.PATH_SEPARATOR);
		}
		return baseHref.concat(sourceId);
	}

	/**
	 * Utworzenie domyślnego kontekstu systemowego.
	 * 
	 * @return instancja obiektu kontekstu systemowego
	 */
	public Context createDefaultContext() {
		String appName = getApplicationName();
		String appVersion = getApplicationVersion();
		String userId = getSystemUserId();
		String roleId = getSystemRoleId();
		String sourceId = getSystemSourceId();
		Context context = new Context();
		context.setAppName(appName).setAppVersion(appVersion)
				.setUser(ContextHelper.createUserReference(userId, getUserHref(userId), /* name */ null))
				.setCurrentRole(ContextHelper.createRoleReference(roleId, getRoleHref(roleId), /* role */ null))
				.setSourceOfRequest(ContextHelper.createSourceOfRequestReference(sourceId, getSourceHref(sourceId),
						/* source */ null));
		context.getSaveRequestContext().setModifyComment(SystemPropertiesDefaults.SYSTEM_COMMENT);
		context.setTimeZone(getSystemTimeZoneID());
		context.setLocale(getSystemLocaleTag());
		context.getQueryRequestContext().setMaxResults(getQueryMaxHits());
		context.getQueryRequestContext().setQueryTimeout(getResponseTimeout());
		context.getQueryRequestContext().setLazilyFetchType(false);
		return context;
	}

	/**
	 * @return ograniczenie na długość wartości typu String
	 */
	public Integer getStringMaxLength() {
		String maxLength = properties.get(MAX_STRING_VALUE_LENGTH);
		if (StringUtils.isNotBlank(maxLength)) {
			try {
				return Integer.parseInt(maxLength);
			} catch (Exception e) {
				logger.warn(String.format("Wrong value of property %s=%s ", MAX_STRING_VALUE_LENGTH, maxLength), e);
			}
		}
		return SystemPropertiesDefaults.MAX_STRING_VALUE_LENGTH;
	}

	/**
	 * @return ograniczenie na długość wartości typu Text
	 */
	public Integer getTextMaxLength() {
		String maxLength = properties.get(MAX_TEXT_VALUE_LENGTH);
		if (StringUtils.isNotBlank(maxLength)) {
			try {
				return Integer.parseInt(maxLength);
			} catch (Exception e) {
				logger.warn(String.format("Wrong value of property %s=%s ", MAX_TEXT_VALUE_LENGTH, maxLength), e);
			}
		}
		return SystemPropertiesDefaults.MAX_TEXT_VALUE_LENGTH;
	}

}
