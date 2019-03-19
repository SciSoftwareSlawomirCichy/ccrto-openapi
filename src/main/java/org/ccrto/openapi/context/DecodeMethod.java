package org.ccrto.openapi.context;

/**
 * 
 * DecodeMethod
 * 
 * <ul>
 * <li>isEncoded="true" - oznacza, że parametr jest w postaci gotowej do zapisu
 * do bazy danych.</li>
 * <li>isEncoded="false" - oznacza, że parametr jest w postaci, którą trzeba
 * dodatkowo przetworzyć (jest zakodowany, decoded) np. data w postaci string'a
 * 'yyyy-MM-dd' trzeba będzie przekształcić do formy przechowywanej w bazie
 * danych czyli liczby milisekund.</li>
 * </ul>
 * 
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public enum DecodeMethod {

	/** wszystkie parametry mają ustawione isEncoded="true" */
	NOTHING,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem parametru
	 * reprezentującego datę (ma ustawiony isEncoded="false")
	 */
	DATE_ONLY,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem parametru
	 * reprezentującego LOB'a (ma ustawiony isEncoded="false")
	 */
	LOB_ONLY,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem
	 * parametrów reprezentującego dat i LOB'ów (mają ustawiony
	 * isEncoded="false")
	 */
	DATE_AND_LOB,
	/**
	 * wszystkie parametry mają ustawione isEncoded="false" z wyjątkiem
	 * parametrów reprezentujących LOB'y (mają ustawiony isEncoded="true")
	 */
	ALL_WITHOUT_LOB,
	/**
	 * wszystkie parametry mają ustawione isEncoded="false"
	 */
	ALL;
}
