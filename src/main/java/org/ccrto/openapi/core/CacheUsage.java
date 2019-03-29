package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * CacheUsage
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlType(name = "CacheUsage")
@XmlEnum
public enum CacheUsage {

	/**
	 * Nie używaj cache podczas pobierania elementu.
	 */
	@XmlEnumValue("NONE")
	NONE,
	/**
	 * Użyj cache podczas pobierania elementu.
	 */
	@XmlEnumValue("TO_USE")
	TO_USE,
	/**
	 * Użyj cache, ale odśwież element tam się znajdujący.
	 */
	@XmlEnumValue("REFRESH")
	REFRESH;

	public static CacheUsage fromValue(String v) {
		return CacheUsage.valueOf(v);
	}

}
