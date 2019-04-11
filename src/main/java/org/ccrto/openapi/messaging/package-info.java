@XmlSchema(namespace = org.ccrto.openapi.XMLSchema.ORGANIZATION_URL + "/" + org.ccrto.openapi.XMLSchema.STANDARD_ACRONYM
		+ "/" + org.ccrto.openapi.XMLSchema.SCHEMA_VERSION + "/"
		+ ObjectFactory.XML_SCHEMA, elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED, xmlns = {
				@XmlNs(prefix = "xsd", namespaceURI = "http://www.w3.org/2001/XMLSchema"),
				@XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance") })
@XmlJavaTypeAdapters({ @XmlJavaTypeAdapter(value = SaveRequestAdapter.class, type = SaveRequest.class) })
package org.ccrto.openapi.messaging;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

import org.ccrto.openapi.messaging.adapters.SaveRequestAdapter;