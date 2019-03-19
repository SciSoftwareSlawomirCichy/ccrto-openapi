package org.ccrto.openapi.values;

import org.apache.commons.lang.StringUtils;

public enum FieldType {

	BOOLEAN("Boolean", BooleanValue.class), 
	TEXT("Text", TextValue.class), 
	STRING("String", StringValue.class), 
	NUMBER("Number", NumberValue.class), 
	DECIMAL("Decimal", NumberValue.class), 
	DOUBLE("Double", NumberValue.class), 
	FLOAT("Float", NumberValue.class), 
	INTEGER("Integer", IntegerValue.class), 
	LONG("Long", IntegerValue.class), 
	CURRENCY("Currency", CurrencyValue.class), 
	DATE_SHORT("Date",	DateValue.class), 
	DATE_LONG("Calendar", DateValue.class), 
	DATE_LOGGER("Timestamp", DateValue.class), 
	DATE_TIME("Time", DateValue.class), 
	URL("URL", URLValue.class), 
	LIST("List", ListValue.class), 
	MAP("Map", MapValue.class), 
	ENTRY("Entry", EntryValue.class), 
	LOB("Lob", LobValue.class), 
	NVP("NameValuePair", NameValuePairValue.class),
	SUBCASE("SubCase", CaseValue.class);

	private final String name;

	private final Class<?> javaType;

	private FieldType(String type, Class<?> javaType) {
		this.name = type;
		this.javaType = javaType;
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
	

	public static FieldType getType(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		for (FieldType dateType : FieldType.values()) {
			if (type.equals(dateType.name)) {
				return dateType;
			}
		}
		return null;
	}

}
