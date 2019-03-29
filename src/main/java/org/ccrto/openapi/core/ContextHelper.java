package org.ccrto.openapi.core;

import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.refs.InvolvementIdentificationRef;
import org.ccrto.openapi.core.refs.UserRoleRef;
import org.ccrto.openapi.core.system.SystemProperties;
import org.ccrto.openapi.core.system.SystemPropertiesDefaults;
import org.ccrto.openapi.entites.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * ContextHelper
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ContextHelper {

	private static final String WRONG_IDENTYFICATION_OF_USER_IN_CONTEXT = "Context has wrong identyfication of user. In context is '%s' but expected '%s'.";
	private static final String USER_IDENTIFICATION_IS_REQUIRED = "User identyfiaction is requred in context!";
	private static final String CONTEXT_HAS_NO_TRUSTED_DATA = "Context has no trusted data! Operation is skip.";

	protected static final Logger logger = LoggerFactory.getLogger(ContextHelper.class);

	private ContextHelper() {
		super();
	}

	/**
	 * Tworzenie kontekstu z danymi użytkownika.
	 * 
	 * @param systemName
	 *            nazwa systemu w celu pobrania domyślnych wartości związanych z
	 *            maksymalnym rozmiarem rezultatu. Może być pusty, wtedy pobrane
	 *            zostaną wartości domyślne dla całego JVM.
	 * @param systemUser
	 *            encja obiektu systemowego reprezentującego użytkownika
	 * @param currentRole
	 *            encja obiektu systemowego reprezentującego role
	 * @param changeComment
	 *            komentarz zmiany pochodzący od użytkownika
	 * @return nowy kontekst oparty o standardowy
	 */
	public static <U extends UserData> Context createContext(String systemName, U systemUser, UserRoleRef currentRole,
			String changeComment) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context baseCopy = systemProperties.createDefaultContext();

		/* dane użytkownika - START */
		String userId = systemUser.getId();
		String userName = systemUser.getName();
		String userHref = systemUser.getHref();
		if (StringUtils.isBlank(userHref)) {
			userHref = systemProperties.getUserHref(userId);
		}
		baseCopy.setUser(createUserReference(userId, userHref, userName));
		/* dane użytkownika - KONIEC */

		/* dane roli użytkownika - START */
		String roleId = currentRole.getId();
		String roleName = currentRole.getRole();
		String roleHref = currentRole.getHref();
		if (StringUtils.isBlank(roleHref)) {
			roleHref = systemProperties.getRoleHref(roleId);
		}
		baseCopy.setCurrentRole(createRoleReference(roleId, roleHref, roleName));
		/* dane roli użytkownika - KONIEC */

		String locale = (StringUtils.isBlank(systemUser.getLocale()) ? baseCopy.getLocale() : systemUser.getLocale());
		String timeZone = (StringUtils.isBlank(systemUser.getTimeZone()) ? baseCopy.getTimeZone()
				: systemUser.getTimeZone());
		baseCopy.setLocale(locale).setTimeZone(timeZone);
		baseCopy.getSaveRequestContext().setModifyComment(changeComment);
		return baseCopy;
	}

	/**
	 * Tworzenie kontekstu z danymi użytkownika.
	 * 
	 * @param systemName
	 *            nazwa systemu w celu pobrania domyślnych wartości związanych z
	 *            maksymalnym rozmiarem rezultatu. Może być pusty, wtedy pobrane
	 *            zostaną wartości domyślne dla całego JVM.
	 * @param userId
	 *            identyfikator, unikalna nazwa użytkownika
	 * @param roleId
	 *            identyfikator, unikalna nazwa roli użytkownika
	 * @param changeComment
	 *            dodatkowy komentarz zmiany
	 * @return nowy kontekst oparty o standardowy
	 */
	public static Context createContext(String systemName, String userId, String roleId, String changeComment) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		Context baseCopy = systemProperties.createDefaultContext()
				.setUser(createUserReference(userId, systemProperties.getUserHref(userId), /* name */ null))
				.setCurrentRole(createRoleReference(roleId, systemProperties.getRoleHref(roleId), /* role */ null));
		baseCopy.getSaveRequestContext().setModifyComment(changeComment);
		return baseCopy;
	}

	/**
	 * Kopiowanie danych pochodzących z kontekstu do instancji obiektu użytkownika
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param user
	 *            instancja obiektu użytkownika
	 * @return informacja czy synchronizacja dane w instancji obiektu użytkownika
	 *         zostały zmienione.
	 */
	public static <U extends UserData> boolean copy2User(Context context, U user) {
		String ctxValue;
		String userValue;
		boolean isChanged = false;

		/* porównuję wymagane dane dotyczące identyfikatora użytkownika */
		InvolvementIdentificationRef ctxUser = context.getUser();
		if (ctxUser == null) {
			/* błąd! identyfikacja użytkownika jest wymagana */
			throw new IllegalArgumentException(USER_IDENTIFICATION_IS_REQUIRED);
		}
		ctxValue = ctxUser.getId();
		userValue = user.getId();
		if (StringUtils.isBlank(ctxValue)) {
			/* błąd! identyfikacja użytkownika jest wymagana */
			throw new IllegalArgumentException(USER_IDENTIFICATION_IS_REQUIRED);
		}
		if (StringUtils.isNotBlank(userValue) && !ctxValue.equalsIgnoreCase(userValue)) {
			/* błąd! próba przepisania danych innemu użytkownikowi */
			throw new IllegalArgumentException(
					String.format(WRONG_IDENTYFICATION_OF_USER_IN_CONTEXT, ctxValue, userValue));
		}
		/* sprawdzam czy dane pochodzące z kontekstu są zaufane */
		if (!context.isTrustedData()) {
			logger.warn(CONTEXT_HAS_NO_TRUSTED_DATA);
			return isChanged;
		}

		/* porównuję pełną nazwę użytkownika */
		ctxValue = ctxUser.getName();
		userValue = user.getName();
		if (StringUtils.isNotBlank(ctxValue) && !ctxValue.equals(userValue)) {
			user.setName(ctxValue);
			isChanged = true;
		}

		/* porównuję locale */
		ctxValue = context.getLocale();
		userValue = user.getLocale();
		/* może być błąd w kontekście */
		if (StringUtils.isBlank(ctxValue) && StringUtils.isNotBlank(userValue)) {
			context.setLocale(userValue);
		}
		if (StringUtils.isNotBlank(ctxValue) && StringUtils.isBlank(userValue)) {
			user.setLocale(ctxValue);
			isChanged = true;
		}

		/* porównuję TimeZone */
		ctxValue = context.getTimeZone();
		userValue = user.getTimeZone();
		/* może być błąd w kontekście */
		if (StringUtils.isBlank(ctxValue) && StringUtils.isNotBlank(userValue)) {
			context.setTimeZone(userValue);
		}
		if (StringUtils.isNotBlank(ctxValue) && StringUtils.isBlank(userValue)) {
			user.setTimeZone(ctxValue);
			isChanged = true;
		}

		return isChanged;
	}

	/**
	 * Zmiana parametru 'maxResults' z jednoczesnym zapisem oryginalnej wartości w
	 * parametrze {@link #ADDITIONAL_PARAM_ORIG_MAX_RESULT}. Przywrócenie wartości
	 * oryginalnej możemy zrealizować za pomocą metody
	 * {@link #returnMaxResults(Context)}.
	 * 
	 * @param systemName
	 *            nazwa systemu w celu pobrania domyślnych wartości związanych z
	 *            maksymalnym rozmiarem rezultatu. Może być pusty, wtedy pobrane
	 *            zostaną wartości domyślne dla całego JVM
	 * @param context
	 *            obiekt kontekstu
	 * @param newMaxResults
	 *            nowy rozmiar wartości 'maxResults'
	 */
	public static void changeMaxResults(String systemName, Context context, Integer newMaxResults) {
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		ContextQueryRequest queryRequestContext = context.getQueryRequestContext();
		if (queryRequestContext == null) {
			queryRequestContext = new ContextQueryRequest();
			context.setQueryRequestContext(queryRequestContext);
		}
		Integer origMaxResults = (queryRequestContext.getMaxResults() == null ? systemProperties.getQueryMaxHits()
				: queryRequestContext.getMaxResults());
		/* ustawiam dodatkowy parametr z oryginalną wartością */
		context.addRequestPropertyValue(SystemProperties.ADDITIONAL_PARAM_ORIG_MAX_RESULT, origMaxResults);
		queryRequestContext.setMaxResults(newMaxResults);
	}

	/**
	 * Przywrócenie 'maxResults' do oryginalnej wartości w parametrze
	 * {@link #ADDITIONAL_PARAM_ORIG_MAX_RESULT}. Wywołanie metody powinno być
	 * poprzedzone metodą {@link #changeMaxResults(Context, Integer)}.
	 * 
	 * @param context
	 *            obiekt kontekstu
	 */
	public static void returnMaxResults(Context context) {
		Integer origMaxResults = (Integer) context
				.getRequestPropertyValue(SystemProperties.ADDITIONAL_PARAM_ORIG_MAX_RESULT);
		ContextQueryRequest queryRequestContext = context.getQueryRequestContext();
		if (queryRequestContext == null) {
			queryRequestContext = new ContextQueryRequest();
			context.setQueryRequestContext(queryRequestContext);
		}
		queryRequestContext.setMaxResults(origMaxResults);
		context.removeRequestProperty(SystemProperties.ADDITIONAL_PARAM_ORIG_MAX_RESULT);
	}

	/**
	 * Ustawienie nazwy systemu w kontekście jako dodatkowy parametr żądania.
	 * 
	 * @param context
	 *            przetwarzany kontekst
	 * @param systemName
	 *            nazwa systemu.
	 */
	public static void setSystemNameInContext(Context context, String systemName) {
		if (StringUtils.isBlank(systemName)) {
			return;
		}
		context.addRequestPropertyValue(SystemProperties.ADDITIONAL_PARAM_SYSTEM_NAME, systemName);
	}

	/**
	 * Pobranie nazwy systemu z obiektu kontekstu
	 * 
	 * @param context
	 *            przetwarzany kontekst
	 * @return nazwa systemu lub {@code null} gdy nazwa nie jest ustawiona.
	 */
	public static String getSystemNameInContext(Context context) {
		Object systemNameInContext = context.getRequestPropertyValue(SystemProperties.ADDITIONAL_PARAM_SYSTEM_NAME);
		if (systemNameInContext instanceof String) {
			return (String) systemNameInContext;
		}
		return null;
	}

	/**
	 * Utworzenie nowej instancji obiektu referencji (linku) użytkownika wg
	 * standardu TMF672
	 * 
	 * @param id
	 *            identyfikator/unikalna nazwa (login, adres e-mail) użytkownika
	 * @param href
	 *            HREF użytkownika
	 * @param name
	 *            opcjonalnie nazwa użytkownika (pełna nazwa użytkownika)
	 * @return instancja obiektu
	 */
	public static ContextUser createUserReference(String id, String href, String name) {
		ContextUser instance = new ContextUser();
		instance.setId(id);
		instance.setHref(href);
		instance.setName(name);
		return instance;
	}

	/**
	 * Utworzenie nowej instancji obiektu referencji (linku) roli użytkownika wg
	 * standardu TMF672
	 * 
	 * @param id
	 *            identyfikator/unikalna nazwa roli
	 * @param href
	 *            HREF użytkownika
	 * @param role
	 *            opcjonalnie dodatkowy opis, rozszerzona nazwa
	 * @return instancja obiektu
	 */
	public static ContextUserRole createRoleReference(String id, String href, String role) {
		ContextUserRole instance = new ContextUserRole();
		instance.setId(id);
		instance.setHref(href);
		instance.setRole(role);
		return instance;
	}

	/**
	 * Utworzenie nowej instancji obiektu referencji (linku) nazwy źródła
	 * reprezentowanego przez system
	 * 
	 * @param id
	 *            identyfikator/unikalna nazwa źródła
	 * @param href
	 *            HREF źródła
	 * @param source
	 *            opcjonalnie dodatkowy opis, rozszerzona nazwa
	 * @return instancja obiektu
	 */
	public static ContextSourceOfRequest createSourceOfRequestReference(String id, String href, String source) {
		ContextSourceOfRequest instance = new ContextSourceOfRequest();
		instance.setId(id);
		instance.setHref(href);
		instance.setSource(source);
		return instance;
	}

	/**
	 * Pobranie obiektu {@link Locale} użytkownika na podstawie przesłanego
	 * kontekstu
	 * 
	 * @param context
	 *            przetwarzany obiekt kontekstu
	 * @return zidentyfikowany obiekt {@link Locale}
	 */
	public static Locale getUserLocale(Context context) {
		String userLocale = null;
		String systemName = null;
		if (context != null) {
			userLocale = context.getLocale();
			systemName = getSystemNameInContext(context);
		}
		return SystemProperties.getUserLocale(systemName, userLocale);
	}

	/**
	 * Pobieranie obiektu strefy czasowej użytkownika na podstawie przesłanego
	 * kontekstu
	 * 
	 * @param context
	 *            przetwarzany obiekt kontekstu
	 * @return obiekt strefy czasowej użytkownika
	 */
	public static TimeZone getUserTimeZone(Context context) {
		String userTimeZone = null;
		String systemName = null;
		if (context != null) {
			userTimeZone = context.getLocale();
			systemName = getSystemNameInContext(context);
		}
		return SystemProperties.getUserTimeZone(systemName, userTimeZone);
	}

	/**
	 * Pobranie formatu daty krótkiej (sam dzień, bez czasu i strefy czasowej)
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return format daty odczytany z kontekstu lub
	 *         {@link SystemPropertiesDefaults#DATE_FORMAT}
	 */
	public static String getShortDateFormatPattern(Context context) {
		String shortDateFormatPattern = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			shortDateFormatPattern = (formats != null ? formats.getDateShortFormat() : null);
			if (StringUtils.isBlank(shortDateFormatPattern)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				shortDateFormatPattern = systemProperties.getDateShortFormat();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			shortDateFormatPattern = systemProperties.getDateShortFormat();
		}
		if (StringUtils.isBlank(shortDateFormatPattern)) {
			return SystemPropertiesDefaults.DATE_FORMAT;
		}
		return shortDateFormatPattern;
	}

	/**
	 * Pobranie formatu daty długiej (dzień oraz czas, oraz strefa czasowa).
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return format daty odczytany z kontekstu lub
	 *         {@link SystemPropertiesDefaults#DATE_XML_FORMAT}
	 */
	public static String getLongDateFormatPattern(Context context) {
		String longDateFormatPattern = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			longDateFormatPattern = (formats != null ? formats.getDateLongFormat() : null);
			if (StringUtils.isBlank(longDateFormatPattern)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				longDateFormatPattern = systemProperties.getDateLongFormat();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			longDateFormatPattern = systemProperties.getDateLongFormat();
		}
		if (StringUtils.isBlank(longDateFormatPattern)) {
			return SystemPropertiesDefaults.DATE_XML_FORMAT;
		}
		return longDateFormatPattern;
	}

	/**
	 * Pobranie kodu waluty.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return odczytany z kontekstu kod waluty lub
	 *         {@link SystemPropertiesDefaults#CURRENCY_CODE}
	 */
	public static String getCurrencyCode(Context context) {
		String currencyCode = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			currencyCode = (formats != null ? formats.getCurrencyCode() : null);
			if (StringUtils.isBlank(currencyCode)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				currencyCode = systemProperties.getCurrencyCode();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			currencyCode = systemProperties.getCurrencyCode();
		}
		if (StringUtils.isBlank(currencyCode)) {
			return SystemPropertiesDefaults.CURRENCY_CODE;
		}
		return currencyCode;
	}

	/**
	 * Pobranie formatu liczby dla wartości reprezentującej cenę.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return odczytany z kontekstu format wartości ceny lub
	 *         {@link SystemPropertiesDefaults#CURRENCY_FORMAT}
	 */
	public static String getCurrencyFormat(Context context) {
		String currencyFormat = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			currencyFormat = (formats != null ? formats.getCurrencyFormat() : null);
			if (StringUtils.isBlank(currencyFormat)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				currencyFormat = systemProperties.getCurrencyFormat();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			currencyFormat = systemProperties.getCurrencyFormat();
		}
		if (StringUtils.isBlank(currencyFormat)) {
			return SystemPropertiesDefaults.CURRENCY_FORMAT;
		}
		return currencyFormat;
	}

	/**
	 * Pobranie formatu liczby dla wartości reprezentującej liczbę
	 * zmiennoprzecinkową.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return odczytany z kontekstu format liczby zmiennoprzecinkowej
	 *         {@link SystemPropertiesDefaults#NUMBER_FORMAT}
	 */
	public static String getNumberFormat(Context context) {
		String numberFormat = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			numberFormat = (formats != null ? formats.getNumberFormat() : null);
			if (StringUtils.isBlank(numberFormat)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				numberFormat = systemProperties.getNumberFormat();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			numberFormat = systemProperties.getNumberFormat();
		}
		if (StringUtils.isBlank(numberFormat)) {
			return SystemPropertiesDefaults.NUMBER_FORMAT;
		}
		return numberFormat;
	}

	/**
	 * Pobranie formatu liczby dla wartości reprezentującej liczbę całkowitą.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @return odczytany z kontekstu format liczby całkowitej
	 *         {@link SystemPropertiesDefaults#INTEGER_FORMAT}
	 */
	public static String getIntegerFormat(Context context) {
		String numberFormat = null;
		SystemProperties systemProperties = null;
		if (context != null) {
			ContextFormats formats = context.getFormats();
			numberFormat = (formats != null ? formats.getIntegerFormat() : null);
			if (StringUtils.isBlank(numberFormat)) {
				String systemName = ContextHelper.getSystemNameInContext(context);
				systemProperties = SystemProperties.getSystemProperties(systemName);
				numberFormat = systemProperties.getIntegerFormat();
			}
		} else {
			systemProperties = SystemProperties.getSystemProperties();
			numberFormat = systemProperties.getIntegerFormat();
		}
		if (StringUtils.isBlank(numberFormat)) {
			return SystemPropertiesDefaults.INTEGER_FORMAT;
		}
		return numberFormat;
	}

	public static DecodeMethod getDecodeMethod(Context context, boolean forRequest) {
		DecodeMethod decodeMethod;
		DecodeMethod decodeContextMethod;
		if (forRequest) {
			decodeContextMethod = context.getDecodeRequest();
		} else {
			decodeContextMethod = context.getDecodeResult();
		}
		if (decodeContextMethod != null) {
			decodeMethod = decodeContextMethod;
		} else {
			decodeMethod = SystemPropertiesDefaults.DECODE_RESULT_AND_REQUEST;
		}
		return decodeMethod;
	}

}
