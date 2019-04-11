package org.ccrto.openapi.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.ccrto.openapi.core.CcrtoProperty;
import org.ccrto.openapi.core.utils.CcrtoJAXBContextChangeRequest.Change;
import org.eclipse.persistence.jaxb.metadata.MetadataSource;
import org.eclipse.persistence.jaxb.xmlmodel.JavaType;
import org.eclipse.persistence.jaxb.xmlmodel.JavaType.JavaAttributes;
import org.eclipse.persistence.jaxb.xmlmodel.XmlBindings;
import org.eclipse.persistence.jaxb.xmlmodel.XmlBindings.JavaTypes;
import org.eclipse.persistence.jaxb.xmlmodel.XmlElement;
import org.eclipse.persistence.jaxb.xmlmodel.XmlRootElement;
import org.eclipse.persistence.jaxb.xmlmodel.XmlVirtualAccessMethods;

public class CcrtoPropertyMetadataSource implements MetadataSource {

	private static XmlBindings bindings;

	private static final Object bindingsLoc = new Object();

	private static final Map<String, JavaType> javaTypes = new HashMap<>();
	private static final Object javaTypesLock = new Object();
	private static final Map<String, Class<? extends CcrtoProperty>> javaAttributes = new HashMap<>();

	private static final CcrtoPropertyMetadataSource instance = new CcrtoPropertyMetadataSource();

	private CcrtoPropertyMetadataSource() {
	}

	@Override
	public XmlBindings getXmlBindings(Map<String, ?> properties, ClassLoader classLoader) {
		return getXmlBindings();
	}

	public static XmlBindings getXmlBindings() {
		synchronized (bindingsLoc) {
			if (bindings == null) {
				bindings = new XmlBindings();
				bindings.setPackageName("org.ccrto.openapi.core");
				JavaTypes javaTypes = new JavaTypes();
				bindings.setJavaTypes(javaTypes);

			}
			return bindings;
		}
	}

	static void addJavaAttribute(List<Change> changes) {
		if (changes != null && !changes.isEmpty()) {
			synchronized (javaTypesLock) {
				boolean isChanged = false;
				for (Change change : changes) {
					String propertyName = change.getPropertyName();
					Class<? extends CcrtoProperty> clazz = change.getClazz();
					if (!javaAttributes.containsKey(propertyName)) {
						javaAttributes.put(propertyName, clazz);
						JavaType javaType = javaTypes.computeIfAbsent("CcrtoPropertyCase",
								k -> createCcrtoPropertyCaseJavaType());
						XmlElement javaAttribute = new XmlElement();
						javaAttribute.setJavaAttribute(propertyName);
						javaAttribute.setType(clazz.getName());
						javaAttribute.setContainerType("java.util.List");
						QName qName = new QName("http://www.eclipse.org/eclipselink/xsds/persistence/oxm",
								"xml-element");
						javaType.getJavaAttributes().getJavaAttribute().add(new JAXBElement<XmlElement>(qName,
								XmlElement.class, /* scope */ null, /* value */ javaAttribute));
						isChanged = true;
					} else if (!clazz.isAssignableFrom(javaAttributes.get(propertyName))) {
						// TODO rozwiązać problem parametrów o tych samych nazwach i różnych typach.
						/* Pomysł: zamienić na typ "Any" */
						isChanged = true;
					}
				}
				if (isChanged) {
					/* Przebudowujemy kontekst */
					CcrtoJAXBContextSecretary.wakeUp();
				}
			}
		}
	}

	private static JavaType createCcrtoPropertyCaseJavaType() {
		/* java type */
		JavaType javaType = new JavaType();
		javaType.setName("CcrtoPropertyCase");

		/* root element */
		XmlRootElement rootElement = new XmlRootElement();
		rootElement.setName("variable");
		javaType.setXmlRootElement(rootElement);

		/* xmlVirtualAccessMethods */
		XmlVirtualAccessMethods accessMethods = new XmlVirtualAccessMethods();
		accessMethods.setGetMethod("get");
		accessMethods.setSetMethod("set");
		javaType.setXmlVirtualAccessMethods(accessMethods);

		JavaAttributes javaAttributes = new JavaAttributes();
		javaType.setJavaAttributes(javaAttributes);
		getXmlBindings().getJavaTypes().getJavaType().add(javaType);
		return javaType;
	}

	public static boolean existsJavaAttribute(String propertyName) {
		synchronized (javaTypesLock) {
			return javaAttributes.containsKey(propertyName);
		}
	}

	/**
	 * @return the {@link #instance}
	 */
	public static CcrtoPropertyMetadataSource getInstance() {
		return instance;
	}
}
