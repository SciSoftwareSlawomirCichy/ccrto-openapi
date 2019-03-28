package org.ccrto.openapi.context;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

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
@XmlType(name = "DecodeMethod")
@XmlEnum
public enum DecodeMethod {

	/** wszystkie parametry mają ustawione isEncoded="true" */
	@XmlEnumValue("NOTHING")
	NOTHING,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem parametru
	 * reprezentującego datę (ma ustawiony isEncoded="false")
	 */
	@XmlEnumValue("DATE_ONLY")
	DATE_ONLY,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem parametru
	 * reprezentującego LOB'a (ma ustawiony isEncoded="false")
	 */
	@XmlEnumValue("LOB_ONLY")
	LOB_ONLY,
	/**
	 * wszystkie parametry mają ustawione isEncoded="true" z wyjątkiem
	 * parametrów reprezentującego dat i LOB'ów (mają ustawiony
	 * isEncoded="false")
	 */
	@XmlEnumValue("DATE_AND_LOB")
	DATE_AND_LOB,
	/**
	 * wszystkie parametry mają ustawione isEncoded="false" z wyjątkiem
	 * parametrów reprezentujących LOB'y (mają ustawiony isEncoded="true")
	 */
	@XmlEnumValue("ALL_WITHOUT_LOB")
	ALL_WITHOUT_LOB,
	/**
	 * wszystkie parametry mają ustawione isEncoded="false"
	 */
	@XmlEnumValue("ALL")
	ALL;
	
	public static DecodeMethod fromValue(String v) {
		return DecodeMethod.valueOf(v);
	}

}
