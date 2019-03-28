package org.ccrto.openapi;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

public class NamespaceUtils {

	private NamespaceUtils() {
	}

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
}
