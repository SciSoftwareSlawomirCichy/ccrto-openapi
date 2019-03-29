package org.ccrto.openapi.core.utils;

import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.ccrto.openapi.core.CcrtoPropertyType;
import org.ccrto.openapi.core.system.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * NumberAnalyser wykrywanie liczb oraz typu boolean opisanego za pomocą
 * wartości {@code true} lub {@code false}.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class NumberValueAnalyser {

	private static final Logger logger = LoggerFactory.getLogger(NumberValueAnalyser.class);

	private static final Map<String, NumberValueAnalyser> locale2PatternMap = new HashMap<>();

	public static final Pattern BOOLEAN_PATTERN = Pattern.compile("^-?[0-1]$");

	public static final Pattern INTEGER_PATTERN = Pattern.compile("^-?\\d+$");

	public static final String NUMBER_PATTERN = "[.,]{0,1}\\d+";

	public static final String CURRENCY_CODE_PATTERN = CcrtoPropertyCurrencyUtils.CURRENCY_CODE_PATTERN + "?";

	/** prosta liczba integer */
	private final Pattern intgerPattern;
	/** prosta liczba */
	private final Pattern numberPattern;
	/** prosta liczba z kodem waluty */
	private final Pattern currencySPattern;
	/** sformatowana (z separatorami grupowania) liczba z kodem waluty */
	private final Pattern currencyFPattern;
	/** separator dziesiętny */
	private final char decimalSeparator;

	private NumberValueAnalyser(Pattern numberPattern, Pattern currencySPattern, Pattern currencyFPattern,
			char decimalSeparator) {
		this.intgerPattern = INTEGER_PATTERN;
		this.numberPattern = numberPattern;
		this.currencySPattern = currencySPattern;
		this.currencyFPattern = currencyFPattern;
		this.decimalSeparator = decimalSeparator;
	}

	/**
	 * Analizowanie wartości 'String' pod kątem czy czasem nie jest on wartością
	 * liczbową. Wykrywane typy:
	 * <ul>
	 * <li>FieldType.BOOLEAN - strings with value {@code true} or {@code false}</li>
	 * <li>FieldType.NUMBER - Numbers of undefined length with or without decimals
	 * (1234.1234)</li>
	 * <li>FieldType.INTEGER - Integers of undefined length</li>
	 * <li>FieldType.CURRENCY - Numbers of undefined length with or without decimals
	 * with ISO 4217 currency code, or formated numbers with thousand
	 * separators</li>
	 * </ul>
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @param locale
	 *            locale użytkownika
	 * @param source
	 *            analizowane słowo
	 * @return wynik analizy lub {@code null} gdy nie zostanie spełniona żadna z
	 *         reguł
	 */
	public static AnalysisResult analyse(String systemName, Locale locale, String source) {

		/* 1. Analiza czy jest to Boolean */
		if ("true".equals(source) || "false".equals(source)) {
			return new AnalysisResult(CcrtoPropertyType.BOOLEAN, /* encoded */true);
		}

		Pattern p;
		NumberValueAnalyser na = getAnalyser(systemName, locale);
		/* 2. Analiza czy jest to liczba */
		p = na.numberPattern;
		if (p.matcher(source).find()) {
			/* Jest to liczba */
			/* 2.1 Analiza czy jest to liczba całkowita */
			p = na.intgerPattern;
			if (p.matcher(source).find()) {
				return new AnalysisResult(CcrtoPropertyType.INTEGER, /* encoded */true);
			}
			return new AnalysisResult(CcrtoPropertyType.NUMBER,
					/* encoded */na.decimalSeparator == '.' || source.contains("."));
		}

		/* 3. Analiza czy jest to liczba z kodem waluty */
		p = na.currencySPattern;
		if (p.matcher(source).find()) {
			/* Jest to liczba z kodem waluty */
			return new AnalysisResult(CcrtoPropertyType.CURRENCY, /* encoded */na.decimalSeparator == '.');
		}

		/* 4. Analiza czy jest to liczba sformatowana z (lub bez) kodem waluty */
		/*
		 * Każdą liczbę sformatowaną z elementami grupującymi traktuję jak liczbę z
		 * walutą
		 */
		p = na.currencyFPattern;
		if (p.matcher(source).find()) {
			/* Jest to liczba sformatowana z (lub bez) kodem waluty */
			return new AnalysisResult(CcrtoPropertyType.CURRENCY, /* encoded */false);
		}
		return null;
	}

	/**
	 * 
	 * AnalysisResult
	 *
	 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
	 * @version $Revision: 1.1 $
	 *
	 */
	public static class AnalysisResult {
		/** Zidentyfikowany typ liczbowy */
		private final CcrtoPropertyType type;
		/**
		 * informacja o tym czy liczba jest już w postaci gotowej do zapisu do bazy
		 * danych.
		 */
		private final boolean encoded;

		public AnalysisResult(CcrtoPropertyType type, boolean encoded) {
			super();
			this.type = type;
			this.encoded = encoded;
		}

		/**
		 * @return the {@link #type}
		 */
		public CcrtoPropertyType getType() {
			return type;
		}

		/**
		 * @return the {@link #encoded}
		 */
		public boolean isEncoded() {
			return encoded;
		}

	}

	/**
	 * Pobranie instancji obiektu wykorzystywanego do analizy.
	 * 
	 * @param systemName
	 *            nazwa systemu
	 * @param localeIn
	 *            locale użytkownika
	 * @return obiekt analizy ze wzorcami liczb.
	 */
	public static NumberValueAnalyser getAnalyser(String systemName, final Locale localeIn) {
		NumberValueAnalyser na;
		Locale locale;
		if (localeIn == null) {
			SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
			locale = systemProperties.getSystemLocale();
		} else {
			locale = localeIn;
		}
		String key = locale.getLanguage();
		if (locale2PatternMap.get(key) != null) {
			na = locale2PatternMap.get(key);
		} else {
			Pattern numberPattern = null;
			Pattern currencyFPattern = null;
			Pattern currencySPattern = null;
			DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(locale);

			/* separator dziesiętny */
			char decimalSep = decimalSymbols.getDecimalSeparator();
			numberPattern = Pattern.compile(createNumberPattern(decimalSep));
			currencySPattern = Pattern.compile(createCurrencyPattern(decimalSep));

			/* separator grupujący (tysiące) */
			char groupingSep = decimalSymbols.getGroupingSeparator();
			currencyFPattern = Pattern.compile(createCurrencyPattern(groupingSep, decimalSep));

			na = new NumberValueAnalyser(numberPattern, currencySPattern, currencyFPattern, decimalSep);
			locale2PatternMap.put(key, na);
		}
		return na;
	}

	public static String createNumberPattern(char decimalSeparator) {
		return "^" + baseNumberPattern(decimalSeparator) + "$";
	}

	private static String baseNumberPattern(char decimalSeparator) {
		if (decimalSeparator == '.') {
			return (new StringBuilder()).append("-?\\d*\\.{0,1}\\d+").toString();
		}
		return (new StringBuilder()).append("-?\\d*[").append(decimalSeparator).append(".]{0,1}\\d+").toString();
	}

	public static String createCurrencyPattern(char decimalSeparator) {
		return "^" + baseNumberPattern(decimalSeparator) + CURRENCY_CODE_PATTERN + "$";
	}

	public static String createCurrencyPattern(char groupingSeparator, char decimalSeparator) {
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("--->createCurrencyPattern: g=%s, d=%s", (int) groupingSeparator,
					(int) decimalSeparator));
		}
		String gS;
		if (groupingSeparator == CcrtoPropertyCurrencyUtils.UNBREAKABLE_SPACE) {
			/* obsługa “Non-breaking space” - dodaję alternatywę zwykłej spacji */
			gS = "[" + groupingSeparator + " ]{1}";
		} else {
			gS = "\\" + groupingSeparator;
		}
		String dS = "\\" + decimalSeparator;
		String decimalClause = "\\d{3})*(" + dS + "\\d{0,2})?|[1-9]{1}\\d{0,}(" + dS + "\\d{0,2})?|0(" + dS
				+ "\\d{0,2})?|(" + dS + "\\d{1,2}))";
		return (new StringBuilder()).append("").append("^\\-?([1-9]{1}[0-9]{0,2}(" + gS + decimalClause)
				.append(CURRENCY_CODE_PATTERN).append("$|").append("^\\-?([1-9]{1}\\d{0,2}(" + gS + decimalClause)
				.append(CURRENCY_CODE_PATTERN).append("$|").append("^\\(([1-9]{1}\\d{0,2}(" + gS + decimalClause)
				.append("\\)").append(CURRENCY_CODE_PATTERN).append("$").toString();
	}

	/**
	 * @return the {@link #intgerPattern}
	 */
	public Pattern getIntgerPattern() {
		return intgerPattern;
	}

	/**
	 * @return the {@link #numberPattern}
	 */
	public Pattern getNumberPattern() {
		return numberPattern;
	}

	/**
	 * @return the {@link #currencySPattern}
	 */
	public Pattern getCurrencySPattern() {
		return currencySPattern;
	}

	/**
	 * @return the {@link #currencyFPattern}
	 */
	public Pattern getCurrencyFPattern() {
		return currencyFPattern;
	}

}
