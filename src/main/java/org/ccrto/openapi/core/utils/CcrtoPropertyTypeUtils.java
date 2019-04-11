package org.ccrto.openapi.core.utils;

public class CcrtoPropertyTypeUtils {

	private CcrtoPropertyTypeUtils() {
	}

	public static final String TYPE_NAME_SEPARATOR = ".";

	/**
	 * Na potrzeby generowania XML potrzebna jest informacja o nazwie elementu
	 * listy. Stała reprezentuje domyślną nazwę elementu listy.
	 */
	public static final String DEFAULT_COLLECTION_ITEM_NAME = "item";

	/**
	 * Na potrzeby generowania XML potrzebna jest informacja o nazwie elementu mapy.
	 * Stała reprezentuje domyślną nazwę elementu.
	 */
	public static final String DEFAULT_MAP_ENTRY_NAME = "entry";

	public static final String ARRAY_SUFFIX = "[]";

	public static final String LOB_PREFIX = "${LOB:";
	public static final String LOB_SUFFIX = "}";

	/**
	 * Metoda zamienia identyfikator obiektu LOB do postaci wartości przechowywanej
	 * jako inny typ niż LOB, np. jako STRING lub TEXT, ale przekroczono maksymalną
	 * liczbę znaków w polu parametru w bazie danych.
	 * 
	 * @param lobId
	 *            identyfikator obiektu LOB
	 * @return odpowiednia wartość przechowywania w bazie danych.
	 */
	public static String encodeValueWithLobId(String lobId) {
		return LOB_PREFIX + lobId + LOB_SUFFIX;
	}

	/**
	 * Metoda wyciąga identyfikator obiektu LOB z wartości jaka jest przechowywana w
	 * bazie danych.
	 * 
	 * @see #encodeValueWithLobId(String)
	 * @param value
	 *            wartość przechowywana w bazie danych
	 * @return identyfikator obiektu LOB
	 */
	public static String decodeValueWithLobId(String value) {
		return value.substring(LOB_PREFIX.length(), value.length() - LOB_SUFFIX.length());
	}

	/**
	 * Metoda sprawdza czy wartość przechowywana w bazie zawiera identyfikator
	 * obiektu LOB
	 * 
	 * @see #encodeValueWithLobId(String)
	 * @see #decodeValueWithLobId(String)
	 * @param value
	 *            wartość przechowywana w bazie danych
	 * @return tak czy nie
	 */
	public static boolean isValueWithLobId(String value) {
		return (value != null && value.startsWith(LOB_PREFIX));
	}

}
