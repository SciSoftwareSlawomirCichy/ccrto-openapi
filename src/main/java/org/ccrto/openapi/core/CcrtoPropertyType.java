package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * CcrtoPropertyType
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlType(name = "CcrtoPropertyType")
@XmlEnum
public enum CcrtoPropertyType {

	/** Represents logical, boolean value */
	@XmlEnumValue("Boolean")
	BOOLEAN(/* name */
			"Boolean",
			/* javaType */
			CcrtoPropertyBoolean.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents string value (string with length less them 256 characters) */
	@XmlEnumValue("String")
	STRING(/* name */
			"String",
			/* javaType */
			CcrtoPropertyString.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents text value (string with length more them 256 characters) */
	@XmlEnumValue("Text")
	TEXT(/* name */
			"Text",
			/* javaType */
			CcrtoPropertyString.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents number value, floating-point number */
	@XmlEnumValue("Number")
	NUMBER(/* name */
			"Number",
			/* javaType */
			CcrtoPropertyNumber.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents number value, floating-point number */
	@XmlEnumValue("Decimal")
	DECIMAL(/* name */
			"Decimal",
			/* javaType */
			CcrtoPropertyNumber.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents number value, floating-point number */
	@XmlEnumValue("Double")
	DOUBLE(/* name */
			"Double",
			/* javaType */
			CcrtoPropertyNumber.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents number value, floating-point number */
	@XmlEnumValue("Float")
	FLOAT(/* name */
			"Float",
			/* javaType */
			CcrtoPropertyNumber.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents integer value */
	@XmlEnumValue("Integer")
	INTEGER(/* name */
			"Integer",
			/* javaType */
			CcrtoPropertyInteger.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false, /* range */ true),
	/** Represents integer/long value */
	@XmlEnumValue("Long")
	LONG(/* name */
			"Long",
			/* javaType */
			CcrtoPropertyInteger.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents currency value */
	@XmlEnumValue("Currency")
	CURRENCY(/* name */
			"Currency",
			/* javaType */
			CcrtoPropertyCurrency.class,
			/* number */
			true,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			true),
	/** Represents date with time value */
	@XmlEnumValue("DateTime")
	DATE_LONG(/* name */
			"DateTime",
			/* javaType */
			CcrtoPropertyDate.class,
			/* number */
			false,
			/* date */
			true,
			/* lob */
			false,
			/* range */
			true),
	/** Represents short date, without time */
	@XmlEnumValue("Date")
	DATE_SHORT(/* name */
			"Date",
			/* javaType */
			CcrtoPropertyDate.class,
			/* number */
			false,
			/* date */
			true,
			/* lob */
			false,
			/* range */
			true),
	/** Represents timestamp value */
	@XmlEnumValue("Timestamp")
	DATE_LOGGER(/* name */
			"Timestamp",
			/* javaType */
			CcrtoPropertyDate.class,
			/* number */
			false,
			/* date */
			true,
			/* lob */
			false,
			/* range */
			true),
	/** Represents time value (only hour with minutes and seconds) */
	@XmlEnumValue("Time")
	DATE_TIME(/* name */
			"Time",
			/* javaType */
			CcrtoPropertyDate.class,
			/* number */
			false,
			/* date */
			true,
			/* lob */
			false,
			/* range */
			true),
	/** Represents URI value */
	@XmlEnumValue("URL")
	URL(/* name */
			"URL",
			/* javaType */
			CcrtoPropertyURL.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents list of values */
	@XmlEnumValue("List")
	LIST(/* name */
			"List",
			/* javaType */
			CcrtoPropertyList.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents map of entries {@link #ENTRY} */
	@XmlEnumValue("Map")
	MAP(/* name */
			"Map",
			/* javaType */
			CcrtoPropertyMap.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents entry value */
	@XmlEnumValue("Entry")
	ENTRY(/* name */
			"Entry",
			/* javaType */
			CcrtoPropertyEntry.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents LOB (Large Object) value */
	@XmlEnumValue("LOB")
	LOB(/* name */
			"Lob",
			/* javaType */
			CcrtoPropertyLob.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			true,
			/* range */
			false),
	/** Represents File value */
	@XmlEnumValue("File")
	FILE(/* name */
			"File",
			/* javaType */
			CcrtoPropertyLob.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents name with value pair value */
	@XmlEnumValue("NameValuePair")
	NVP(/* name */
			"NameValuePair",
			/* javaType */
			CcrtoPropertyNameValuePair.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents case value in main case (subcase) */
	@XmlEnumValue("SubCase")
	SUBCASE(/* name */
			"SubCase",
			/* javaType */
			CcrtoPropertyCase.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false),
	/** Represents any value */
	@XmlEnumValue("ANY")
	ANY(/* name */
			"ANY",
			/* javaType */
			CcrtoPropertyAny.class,
			/* number */
			false,
			/* date */
			false,
			/* lob */
			false,
			/* range */
			false);

	private final String name;

	private final Class<?> javaType;

	private final boolean number;

	private final boolean date;

	private final boolean lob;

	private final boolean range;

	private CcrtoPropertyType(String name, Class<?> javaType, boolean number, boolean date, boolean lob,
			boolean range) {
		this.name = name;
		this.javaType = javaType;
		this.number = number;
		this.date = date;
		this.lob = lob;
		this.range = range;
	}

	/**
	 * @return the {@link #type}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the {@link #javaType}
	 */
	public Class<?> getJavaType() {
		return javaType;
	}

	public static CcrtoPropertyType getType(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		for (CcrtoPropertyType fieldType : CcrtoPropertyType.values()) {
			if (type.equals(fieldType.name)) {
				return fieldType;
			}
		}
		return null;
	}

	public static CcrtoPropertyType fromValue(String v) {
		CcrtoPropertyType fieldType = getType(v);
		if (fieldType == null) {
			throw new IllegalArgumentException(v);
		}
		return fieldType;
	}

	/**
	 * Pobieranie obiektu typu na podstawie klasy implementacji Java
	 * 
	 * @param clazz
	 *            definicja klasy
	 * @return reprezentacja {@link CcrtoPropertyType} jeżeli nie zostanie
	 *         znaleziony to {@link #ANY}
	 */
	public static final CcrtoPropertyType classOf(Class<?> clazz) {
		if (clazz == null) {
			return ANY;
		}
		for (CcrtoPropertyType fieldType : CcrtoPropertyType.values()) {
			if (fieldType.getJavaType().getName().equals(clazz.getName())) {
				return fieldType;
			}
		}
		return ANY;
	}

	/**
	 * @return the {@link #number}
	 */
	public boolean isNumber() {
		return number;
	}

	/**
	 * @return the {@link #date}
	 */
	public boolean isDate() {
		return date;
	}

	/**
	 * @return the {@link #lob}
	 */
	public boolean isLob() {
		return lob;
	}

	/**
	 * @return the {@link #range}
	 */
	public boolean isRange() {
		return range;
	}

}
