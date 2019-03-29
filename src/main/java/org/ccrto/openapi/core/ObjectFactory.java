package org.ccrto.openapi.core;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	public static final String XML_SCHEMA = "Core";

	private static Map<String, QName> dynamicQNameMap = new HashMap<>();

	public static QName getQName(final String schemaName, final String propertyName) {
		String namespaceUri = getSchemaNamespace(schemaName);
		StringBuilder qNameKey = new StringBuilder(namespaceUri);
		qNameKey.append('/').append(propertyName);
		return dynamicQNameMap.computeIfAbsent(qNameKey.toString(), k -> new QName(namespaceUri, propertyName));
	}

	private static Map<String, String> schemaNamespaces = new HashMap<>();

	public static String getSchemaNamespace(final String schemaName) {
		return schemaNamespaces.computeIfAbsent(schemaName, k -> {
			StringBuilder schemaNamespace = new StringBuilder(org.ccrto.openapi.XMLSchema.ORGANIZATION_URL);
			schemaNamespace.append('/').append(org.ccrto.openapi.XMLSchema.STANDARD_ACRONYM).append('/')
					.append(org.ccrto.openapi.XMLSchema.SCHEMA_VERSION).append('/').append(schemaName);
			return schemaNamespace.toString();
		});
	}

	private static final Map<Class<? extends CcrtoProperty>, String> defaultProperty2Type = new HashMap<>();

	static {
		defaultProperty2Type.put(CcrtoPropertyAny.class, CcrtoPropertyType.ANY.getName());
		defaultProperty2Type.put(CcrtoPropertyBoolean.class, CcrtoPropertyType.BOOLEAN.getName());
		defaultProperty2Type.put(CcrtoPropertyCurrency.class, CcrtoPropertyType.CURRENCY.getName());
		defaultProperty2Type.put(CcrtoPropertyDate.class, CcrtoPropertyType.DATE_LONG.getName());
		defaultProperty2Type.put(CcrtoPropertyEntry.class, CcrtoPropertyType.ENTRY.getName());
		defaultProperty2Type.put(CcrtoPropertyInteger.class, CcrtoPropertyType.INTEGER.getName());
		defaultProperty2Type.put(CcrtoPropertyList.class, CcrtoPropertyType.LIST.getName());
		defaultProperty2Type.put(CcrtoPropertyLob.class, CcrtoPropertyType.LOB.getName());
		defaultProperty2Type.put(CcrtoPropertyMap.class, CcrtoPropertyType.MAP.getName());
		defaultProperty2Type.put(CcrtoPropertyNameValuePair.class, CcrtoPropertyType.NVP.getName());
		defaultProperty2Type.put(CcrtoPropertyNumber.class, CcrtoPropertyType.NUMBER.getName());
		defaultProperty2Type.put(CcrtoPropertyString.class, CcrtoPropertyType.STRING.getName());
	}

	public static String getDefaultPropertyType(Class<? extends CcrtoProperty> clazz) {
		return defaultProperty2Type.get(clazz);
	}

	public CcrtoProperty createCcrtoProperty() {
		return new CcrtoProperty();
	}

	public CcrtoPropertyBoolean createCcrtoPropertyBoolean() {
		return new CcrtoPropertyBoolean();
	}

	public CcrtoPropertyCase createCcrtoPropertyCase() {
		return new CcrtoPropertyCase();
	}

	public CcrtoPropertyCurrency createCcrtoPropertyCurrency() {
		return new CcrtoPropertyCurrency();
	}

	public CcrtoPropertyDate createCcrtoPropertyDate() {
		return new CcrtoPropertyDate();
	}

	public CcrtoPropertyEntry createCcrtoPropertyEntry() {
		return new CcrtoPropertyEntry();
	}

	public CcrtoPropertyInteger createCcrtoPropertyInteger() {
		return new CcrtoPropertyInteger();
	}

	public CcrtoPropertyList createCcrtoPropertyList() {
		return new CcrtoPropertyList();
	}

	public CcrtoPropertyLob createCcrtoPropertyLob() {
		return new CcrtoPropertyLob();
	}

	public CcrtoPropertyMap createCcrtoPropertyMap() {
		return new CcrtoPropertyMap();
	}

	public CcrtoPropertyNameValuePair createCcrtoPropertyNameValuePair() {
		return new CcrtoPropertyNameValuePair();
	}

	public CcrtoPropertyNumber createCcrtoPropertyNumber() {
		return new CcrtoPropertyNumber();
	}

	public CcrtoPropertyString createCcrtoPropertyString() {
		return new CcrtoPropertyString();
	}

}
