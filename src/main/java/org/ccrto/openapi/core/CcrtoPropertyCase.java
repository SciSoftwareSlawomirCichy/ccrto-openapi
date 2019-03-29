package org.ccrto.openapi.core;

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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.adapters.CcrtoPropertyAdapter;
import org.ccrto.openapi.core.internal.IValueCase;
import org.eclipse.persistence.oxm.annotations.XmlVariableNode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "variable")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Case")
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

	// @XmlJavaTypeAdapter(CcrtoPropertyAdapter.class)
	// @XmlVariableNode(value = "propertyName", type = CcrtoProperty.class)
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

	/* Overridden (non-Javadoc) */
	public int size() {
		return getCaseProperties().size();
	}

	/* Overridden (non-Javadoc) */
	public boolean isEmpty() {
		return getCaseProperties().isEmpty();
	}

	/* Overridden (non-Javadoc) */
	public boolean contains(CcrtoProperty o) {
		return getCaseProperties().contains(o);
	}

	/* Overridden (non-Javadoc) */
	public boolean add(CcrtoProperty e) {
		if (StringUtils.isBlank(e.propertyName)) {
			throw new IllegalArgumentException("Property name is required!");
		}
		if (getCaseProperties().add(e)) {
			getProperties().add(new JAXBElement<CcrtoProperty>(
					ObjectFactory.getQName(ObjectFactory.XML_SCHEMA, e.propertyName), CcrtoProperty.class, e));
			return true;
		}
		return false;
	}

	@Override
	public boolean isNull() {
		return false;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((caseHeader == null) ? 0 : caseHeader.hashCode());
		result = prime * result + ((caseProperties == null) ? 0 : caseProperties.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof CcrtoPropertyCase)) {
			return false;
		}
		CcrtoPropertyCase other = (CcrtoPropertyCase) obj;
		if (caseHeader == null) {
			if (other.caseHeader != null) {
				return false;
			}
		} else if (!caseHeader.equals(other.caseHeader)) {
			return false;
		}
		if (caseProperties == null) {
			if (other.caseProperties != null) {
				return false;
			}
		} else if (!caseProperties.equals(other.caseProperties)) {
			return false;
		}
		if (status != other.status) {
			return false;
		}
		return true;
	}

	/**
	 * @return the {@link #properties}
	 */
	public List<JAXBElement<CcrtoProperty>> getProperties() {
		if (properties == null) {
			properties = new ArrayList<>();
		}
		return properties;
	}

}
