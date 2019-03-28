package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * CcrtoPropertyStatus - informacja o stanie obiektu sprawy/listy.
 * 
 * <b>UWAGA!</b> Nie wszystkie statusy można używać we wszystkich typach
 * wartości - nieprawidłowe użycie statusów może sprawić, że system zareaguje w
 * nieoczekiwany sposób. Aby zweryfikować jaki status można używać dla jakich
 * obiektów użyj metod {@link #isValueCaseStatus()} oraz
 * {@link #isValueListStatus()}
 *
 * 
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlType(name = "CcrtoPropertyStatus")
@XmlEnum
public enum CcrtoPropertyStatus {

	/** Przesłane dane o obiekcie są pełne */
	@XmlEnumValue("ALL")
	ALL,
	/**
	 * Dotyczy list obiektów zależnych - przesłana jest pusta lista, ale to nie
	 * znaczy, że nie ma spraw zależnych - nie należy wtedy usuwać starych danych
	 */
	@XmlEnumValue("TRUNCATED")
	TRUNCATED,
	/** Przesłany obiekt reprezentuje wartość pustą */
	@XmlEnumValue("NULL")
	NULL,
	/**
	 * Dotyczy list obiektów zależnych - możliwe, ze lista obiektów jest tak długa,
	 * ze przesyłamy pierwszą partię danych, nie należy wtedy usuwać starych danych.
	 */
	@XmlEnumValue("FRAGMENT")
	FRAGMENT,
	/**
	 * Dotyczy obiektów spraw - przesłany jest tylko nagłówek sprawy - nie
	 * aktualizuj parametrów, typu itd.
	 */
	@XmlEnumValue("ONLY_HEADER")
	ONLY_HEADER;

	/** Domyślny status obiektu sprawy */
	public static final CcrtoPropertyStatus DEFAULT_STATUS = ALL;

	/**
	 * Czy status można użyć dla obiektów spraw?
	 * 
	 * @return tak albo nie
	 */
	public boolean isValueCaseStatus() {
		switch (this) {
		case ALL:
		case NULL:
		case ONLY_HEADER:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Czy status można użyć dla list?
	 * 
	 * @return tak albo nie
	 */
	public boolean isValueListStatus() {
		return !ONLY_HEADER.equals(this);
	}

	public static CcrtoPropertyStatus fromValue(String v) {
		if (StringUtils.isBlank(v)) {
			return DEFAULT_STATUS;
		}
		return CcrtoPropertyStatus.valueOf(v);
	}

}
