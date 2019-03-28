package org.ccrto.openapi.values;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.NamespaceUtils;
import org.ccrto.openapi.caseheader.CaseHeader;
import org.ccrto.openapi.values.api.IValueCase;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoCase")
public class CcrtoPropertyCase extends CcrtoProperty implements IValueCase {

	private static final long serialVersionUID = 4800120543973802924L;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute(name = "status", required = false)
	private CcrtoPropertyStatus status;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false, nillable = false)
	private CaseHeader caseHeader;

	private List<CcrtoProperty> caseProperties;

	@XmlAnyElement(lax = true)
	private List<JAXBElement<CcrtoProperty>> properties;

	/**
	 * @return the {@link #status}
	 */
	public CcrtoPropertyStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	public void setStatus(CcrtoPropertyStatus status) {
		this.status = status;
	}

	/**
	 * @return the {@link #caseProperties}
	 */
	public List<CcrtoProperty> getCaseProperties() {
		if (caseProperties == null) {
			caseProperties = new ArrayList<>();
			if (!getJAXBProperties().isEmpty()) {
				for (JAXBElement<CcrtoProperty> jaxbProperty : getJAXBProperties()) {
					CcrtoProperty caseProperty = jaxbProperty.getValue();
					caseProperty.setPropertyName(jaxbProperty.getName().getLocalPart());
					caseProperties.add(caseProperty);
				}
			}
		}
		return caseProperties;
	}

	/**
	 * @return the {@link #caseHeader}
	 */
	public CaseHeader getCaseHeader() {
		return caseHeader;
	}

	/**
	 * @param caseHeader
	 *            the {@link #caseHeader} to set
	 */
	public void setCaseHeader(CaseHeader caseHeader) {
		this.caseHeader = caseHeader;
	}

	@Override
	public boolean isNull() {
		return false;
	}

	/* Overridden (non-Javadoc) */
	public int size() {
		return getCaseProperties().size();
	}

	/* Overridden (non-Javadoc) */
	public boolean isEmpty() {
		return getCaseProperties().isEmpty();
	}

	/* Overridden (non-Javadoc) */
	public boolean contains(Object o) {
		return getCaseProperties().contains(o);
	}

	/* Overridden (non-Javadoc) */
	public boolean add(CcrtoProperty e) {
		if (StringUtils.isBlank(e.propertyName)) {
			throw new IllegalArgumentException("Property name is required!");
		}
		if (getCaseProperties().add(e)) {
			QName propertyQName = NamespaceUtils.getQName(ObjectFactory.XML_SCHEMA, e.propertyName);
			getJAXBProperties().add(new JAXBElement<CcrtoProperty>(propertyQName, CcrtoProperty.class, e));
			return true;
		}
		return false;
	}

	private List<JAXBElement<CcrtoProperty>> getJAXBProperties() {
		if (properties == null) {
			properties = new ArrayList<>();
		}
		return properties;
	}

}
