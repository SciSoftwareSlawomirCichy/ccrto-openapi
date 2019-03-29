package org.ccrto.openapi.core.utils;

import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.system.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * DateAnalyser - analizator daty. Czy wartość reprezentowana przez dany
 * 'String' jest datą w odpowiednim formacie. Mechanizm wykorzystuje
 * wygenerowany w {@link #dateFormat2Regexp(String, Locale)} dla danego formatu
 * daty.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class DateValueAnalyser {

	private static final Logger logger = LoggerFactory.getLogger(DateValueAnalyser.class);

	private static final Map<String, String> formatElement2PatternMap = new HashMap<>();
	private static final Map<String, DateValueAnalyser> formatDate2AnalyserMap = new HashMap<>();

	private String format;
	private Pattern dateRegexp;

	/**
	 * Analiza wartości typu "String" pod kątem, czy jest on może reprezentacją daty
	 * w zadanym formacie.
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @param dateFormat
	 *            format daty
	 * @param locale
	 *            locale użytkownika
	 * @param source
	 *            analizowane słowo
	 * @return prawda, jeżeli reprezentacja jest zgodna z zadanym formatem daty.
	 */
	public static boolean analyse(String systemName, String dateFormat, Locale locale, String source) {
		Pattern p;
		DateValueAnalyser dateAnalyser = getAnalyser(systemName, dateFormat, locale);
		p = dateAnalyser.dateRegexp;
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("--->analyse[base]: '%s': '%s'", dateFormat, p.pattern()));
		}
		return p.matcher(source).find();
	}

	/**
	 * Pobranie obiekty analizatora daty dla określonych locale użytkownika.
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @param dateFormatPattern
	 *            format daty
	 * @param localeIn
	 *            locale użytkownika
	 * @return obiekt analizatora
	 */
	public static DateValueAnalyser getAnalyser(final String systemName, final String dateFormatPattern,
			final Locale localeIn) {
		Locale locale;
		if (localeIn == null) {
			SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
			locale = systemProperties.getSystemLocale();
		} else {
			locale = localeIn;
		}
		String dateAnalyserKey = createFormatElementKey(dateFormatPattern, locale);
		if (formatDate2AnalyserMap.get(dateAnalyserKey) != null) {
			return formatDate2AnalyserMap.get(dateAnalyserKey);
		}

		String baseRegexp = dateFormat2Regexp(dateFormatPattern, locale);
		DateValueAnalyser result = new DateValueAnalyser();
		if (StringUtils.isNotBlank(baseRegexp)) {
			result.dateRegexp = Pattern.compile("^" + baseRegexp + "$");
		}
		result.format = dateFormatPattern;
		formatDate2AnalyserMap.put(dateAnalyserKey, result);
		return result;
	}

	/**
	 * Dekodowanie formatu daty do postaci klauzuli regexp
	 * 
	 * @param dateFormat
	 *            format daty
	 * @param userLocale
	 *            locale użytkownika
	 * @return klauzula regexp odpowiadająca danemu formatowi daty.
	 */
	public static String dateFormat2Regexp(String dateFormat, Locale userLocale) {

		StringBuilder odp = new StringBuilder();
		StringBuilder element = null;
		int tLength = dateFormat.length();
		int previousChar = -1;
		int counts = 0;
		boolean startToken = false;
		boolean addIgnoreCaseSensitive = false;
		for (int i = 0; i < tLength; i++) {
			int znak = (int) dateFormat.charAt(i);
			if (znak > 255) {
				continue;
			}
			if (previousChar != znak) {
				previousChar = znak;
				counts = 0;
				if (!startToken && element != null) {
					odp.append(element.toString());
					element = null;
				}
			} else {
				counts++;
			}
			switch (znak) {
			case 'G': // Era designator
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(");
					element.append(loadEraPattern("G", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				}
				break;
			case 'y': // Year
			case 'Y': // Week year
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,4})");
				} else if (counts < 3) {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append(",4})");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'M': // Month in year
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([0-1][0-9])");
				} else if (counts == 2) {
					element = new StringBuilder();
					element.append("(");
					element.append(loadMonthPattern("MMM", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				} else if (counts == 3) {
					element = new StringBuilder();
					element.append("(");
					element.append(loadMonthPattern("MMMM", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				}
				break;
			case 'w': // Week in year
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'F': // Day of week in month
			case 'W': // Week in month
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1})");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'D': // Day in year
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,3})");
				} else if (counts < 2) {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append(",3})");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'd': // Day in month
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([0-3][0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'E': // Day name in week
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts < 3) {
					element = new StringBuilder();
					element.append("(");
					element.append(loadWeekDayPattern("E", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				} else {
					element = new StringBuilder();
					element.append("(");
					element.append(loadWeekDayPattern("EEEE", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				}
				break;
			case 'a': // Am/pm marker
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(");
					element.append(loadAmPmPattern("a", userLocale));
					element.append(")");
					addIgnoreCaseSensitive = true;
				}
				break;
			case 'H': // Hour in day (0-23)
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([0-2][0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'k': // Hour in day (1-24)
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([1-2][0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'K': // Hour in am/pm (0-11)
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([0-1][0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'h': // Hour in am/pm (1-12)
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("(1[0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'm': // Minute in hour
			case 's': // Second in minute
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,2})");
				} else if (counts == 1) {
					element = new StringBuilder();
					element.append("([0-5][0-9])");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'S': // Millisecond
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("(\\d{1,3})");
				} else if (counts < 2) {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append(",3})");
				} else {
					element = new StringBuilder();
					element.append("(\\d{").append(counts + 1).append("})");
				}
				break;
			case 'z': // Time zone, General time zone
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else {
					element = new StringBuilder();
					element.append("(\\w+)");
				}
				break;
			case 'Z': // Time zone, RFC 822 time zone
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else {
					element = new StringBuilder();
					element.append("([+-][0-2][0-9][0-5][0-9])");
				}
				break;
			case 'X': // Time zone, ISO 8601 time zone
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else if (counts == 0) {
					element = new StringBuilder();
					element.append("([+-][0-2][0-9])");
				} else if (counts < 2) {
					element = new StringBuilder();
					element.append("([+-][0-2][0-9][0-5][0-9])");
				} else {
					element = new StringBuilder();
					element.append("([+-][0-2][0-9]:[0-5][0-9])");
				}
				break;
			case '\'':
				if (counts == 0 && !startToken) {
					element = new StringBuilder();
					startToken = true;
				} else if (counts != 0 && element != null) {
					element.append(dateFormat.charAt(i));
					startToken = false;
				} else {
					startToken = false;
				}
				break;
			case '-': // date separator
			case ':': // date separator
			case ',': // date separator
			case '.': // date separator
			case ' ': // date separator
			case '/': // date separator
				if (startToken) {
					element.append(znak);
				} else {
					if (counts == 0) {
						element = new StringBuilder();
						element.append(dateFormat.charAt(i));
					} else if (element != null) {
						element.append(dateFormat.charAt(i));
					}
				}
				break;
			case '\\':
				if (startToken) {
					element.append(dateFormat.charAt(i)).append(dateFormat.charAt(i));
				} else {
					throw new IllegalArgumentException(
							String.format("Illegal pattern character '%s'", dateFormat.charAt(i)));
				}
				break;
			case 'u': // Day number of week (1 = Monday, ..., 7 = Sunday)
			default:
				if (startToken) {
					element.append(dateFormat.charAt(i));
				} else {
					throw new IllegalArgumentException(
							String.format("Illegal pattern character '%s'", dateFormat.charAt(i)));
				}
				break;
			}
		}
		if (element != null) {
			odp.append(element.toString());
		}
		return (addIgnoreCaseSensitive ? "(?i)" : "") + odp.toString();
	}

	/**
	 * Załadowanie wzorca słownika miesięcy.
	 * 
	 * @param formatElement
	 *            format elementu miesiąca ('M')
	 * @param locale
	 *            locale użytkownika
	 * @return wzorzec do wyszukiwania miesięcy
	 */
	public static String loadMonthPattern(String formatElement, Locale locale) {
		if (StringUtils.isBlank(formatElement) || !formatElement.contains("M")) {
			return null;
		}
		String formatElementKey = createFormatElementKey(formatElement, locale);
		if (formatElement2PatternMap.get(formatElementKey) != null) {
			return formatElement2PatternMap.get(formatElementKey);
		}

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		StringBuilder paternSB = new StringBuilder();
		if (formatElement.length() > 3) {
			/* Ładowanie symboli dla 'MMMM' i dłuższych - START */
			int i = 0;
			for (String monthName : symbols.getMonths()) {
				if (StringUtils.isNotBlank(monthName)) {
					if (i != 0) {
						paternSB.append("|");
					}
					paternSB.append(monthName);
					i++;
				}
			}
			/* Ładowanie symboli dla 'MMMM' i dłuższych - KONIEC */
		} else if (formatElement.length() > 2) {
			/* Ładowanie symboli dla 'MMM' - START */
			int i = 0;
			for (String monthName : symbols.getShortMonths()) {
				if (StringUtils.isNotBlank(monthName)) {
					if (i != 0) {
						paternSB.append("|");
					}
					paternSB.append(monthName);
					i++;
				}
			}
			/* Ładowanie symboli dla 'MMM' - KONIEC */
		} else {
			for (int i = 0; i < 12; i++) {
				if (i != 0) {
					paternSB.append("|");
				}
				String monthNumber = Integer.toString(i + 1);
				if (monthNumber.length() < 2 && formatElement.length() == 2) {
					paternSB.append("0");
				}
				paternSB.append(monthNumber);
			}
		}

		String value = paternSB.toString();
		formatElement2PatternMap.put(formatElementKey, value);
		return value;
	}

	/**
	 * Załadowanie wzorca słownika nazw dni tygodnia.
	 * 
	 * @param formatElement
	 *            format elementu dnia tygodnia ('E')
	 * @param locale
	 *            locale użytkownika
	 * @return wzorzec do wyszukiwania dni tygodnia
	 */
	public static String loadWeekDayPattern(String formatElement, Locale locale) {
		if (StringUtils.isBlank(formatElement) || !formatElement.contains("E")) {
			return null;
		}
		String formatElementKey = createFormatElementKey(formatElement, locale);
		if (formatElement2PatternMap.get(formatElementKey) != null) {
			return formatElement2PatternMap.get(formatElementKey);
		}

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		StringBuilder paternSB = new StringBuilder();
		if (formatElement.length() > 3) {
			/* Ładowanie symboli dla 'EEEE' i dłuższych - START */
			int i = 0;
			for (String dayName : symbols.getWeekdays()) {
				if (StringUtils.isNotBlank(dayName)) {
					if (i != 0) {
						paternSB.append("|");
					}
					paternSB.append(dayName);
					i++;
				}
			}
			/* Ładowanie symboli dla 'EEEE' i dłuższych - KONIEC */
		} else {
			/* Ładowanie symboli dla 'E - START */
			int i = 0;
			for (String dayName : symbols.getShortWeekdays()) {
				if (StringUtils.isNotBlank(dayName)) {
					if (i != 0) {
						paternSB.append("|");
					}
					paternSB.append(dayName);
					i++;
				}
			}
			/* Ładowanie symboli dla 'E' - KONIEC */
		}

		String value = paternSB.toString();
		formatElement2PatternMap.put(formatElementKey, value);
		return value;
	}

	/**
	 * Załadowanie wzorca słownika nazw ery.
	 * 
	 * @param formatElement
	 *            format elementu ery ('G')
	 * @param locale
	 *            locale użytkownika
	 * @return wzorzec do wyszukiwania ery
	 */
	public static String loadEraPattern(String formatElement, Locale locale) {
		if (StringUtils.isBlank(formatElement) || !formatElement.contains("G")) {
			return null;
		}
		String formatElementKey = createFormatElementKey(formatElement, locale);
		if (formatElement2PatternMap.get(formatElementKey) != null) {
			return formatElement2PatternMap.get(formatElementKey);
		}

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		StringBuilder paternSB = new StringBuilder();
		paternSB.append(symbols.getEras()[0]);
		paternSB.append("|");
		paternSB.append(symbols.getEras()[1]);

		String value = paternSB.toString();
		formatElement2PatternMap.put(formatElementKey, value);
		return value;
	}

	/**
	 * Załadowanie wzorca słownika elementów 'PM' oraz 'AM'.
	 * 
	 * @param formatElement
	 *            format elementu ('a')
	 * @param locale
	 *            locale użytkownika
	 * @return wzorzec do wyszukiwania elementów 'PM' oraz 'AM
	 */
	public static String loadAmPmPattern(String formatElement, Locale locale) {
		if (StringUtils.isBlank(formatElement) || !formatElement.contains("a")) {
			return null;
		}
		String formatElementKey = createFormatElementKey(formatElement, locale);
		if (formatElement2PatternMap.get(formatElementKey) != null) {
			return formatElement2PatternMap.get(formatElementKey);
		}

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		StringBuilder paternSB = new StringBuilder();
		paternSB.append(symbols.getAmPmStrings()[0]);
		paternSB.append("|");
		paternSB.append(symbols.getAmPmStrings()[1]);

		String value = paternSB.toString();
		formatElement2PatternMap.put(formatElementKey, value);
		return value;
	}

	/**
	 * Załadowanie wzorca nazw stref czasowych
	 * 
	 * @param formatElement
	 *            format elementu ('z')
	 * @param locale
	 *            locale użytkownika
	 * @param timeZone
	 *            strefa czasowa użytkownika
	 * @return lista nazw danej strefy czasowej użytkownika
	 */
	public static String loadTimeZoneStringPattern(String formatElement, Locale locale, TimeZone timeZone) {
		if (StringUtils.isBlank(formatElement) || !formatElement.contains("z")) {
			return null;
		}
		String formatElementKey = createFormatElementKey(formatElement, locale).concat(".").concat(timeZone.getID());
		if (formatElement2PatternMap.get(formatElementKey) != null) {
			return formatElement2PatternMap.get(formatElementKey);
		}

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(locale);
		Set<String> potentialNames = new HashSet<String>();
		for (String[] timeZoneDefinition : symbols.getZoneStrings()) {
			/**
			 * <li>zoneStrings[i][0] - time zone ID</li>
			 * <li>zoneStrings[i][1] - long name of zone in standard time</li>
			 * <li>zoneStrings[i][2] - short name of zone in standard time</li>
			 * <li>zoneStrings[i][3] - long name of zone in daylight saving time</li>
			 * <li>zoneStrings[i][4] - short name of zone in daylight saving time</li>
			 */
			if (timeZoneDefinition[0].equals(timeZone.getID())) {
				potentialNames.add(timeZoneDefinition[1]);
				potentialNames.add(timeZoneDefinition[2]);
				potentialNames.add(timeZoneDefinition[3]);
				potentialNames.add(timeZoneDefinition[4]);
			}
		}
		StringBuilder paternSB = new StringBuilder();
		int i = 0;
		for (String timeZoneName : potentialNames) {
			if (StringUtils.isNotBlank(timeZoneName)) {
				if (i != 0) {
					paternSB.append("|");
				}
				paternSB.append(timeZoneName);
				i++;
			}
		}
		String value = paternSB.toString();
		formatElement2PatternMap.put(formatElementKey, value);
		return value;

	}

	/**
	 * Utworzenie klucza dla map z danymi przechowywanymi w pamięci podręcznej.
	 * 
	 * @param formatElement
	 *            element formatu
	 * @param locale
	 *            locale użytkownika
	 * @return utworzony klucz
	 */
	private static String createFormatElementKey(String formatElement, Locale locale) {
		return formatElement.concat(".").concat(locale.getLanguage());
	}

	/**
	 * @return the {@link #format}
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the {@link #format} to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the {@link #dateRegexp}
	 */
	public Pattern getDateRegexp() {
		return dateRegexp;
	}

	/**
	 * @param dateRegexp
	 *            the {@link #dateRegexp} to set
	 */
	public void setDateRegexp(Pattern dateRegexp) {
		this.dateRegexp = dateRegexp;
	}

}