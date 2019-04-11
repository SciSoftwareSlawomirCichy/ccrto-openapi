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
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueCase;
import org.ccrto.openapi.core.utils.CcrtoPropertyMetadataSource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = ObjectFactory.TAG_MAIN_DOCUMENT)
@XmlType(name = "Case")
@XmlAccessorType(XmlAccessType.NONE)
public class CcrtoPropertyCase extends CcrtoProperty implements IValueCase {

	private static final long serialVersionUID = 4800120543973802924L;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute(name = "status", required = false)
	private CcrtoPropertyStatus status;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute(name = "withRequiredPosition", required = false)
	private Boolean withRequiredPosition;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = true, nillable = false)
	private CaseHeader caseHeader;

	@XmlAnyElement(lax = true)
	protected List<JAXBElement<CcrtoProperty>> anyProperties;

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

	/**
	 * Metoda pobierająca element XML po nazwie tag'a (wirtualne metody). Użycie
	 * metody zadeklarowane jest w {@link CcrtoPropertyMetadataSource}
	 * 
	 * @param name
	 *            nazwa pola
	 * @return obiekt odpowiadający polu
	 */
	public Object get(String name) {
		// TODO zastanowić się czy implementować
		return null;
	}

	/**
	 * Metoda ustawiająca element XML po nazwie tag'a (wirtualne metody). Użycie
	 * metody zadeklarowane jest w {@link CcrtoPropertyMetadataSource}
	 * 
	 * @param name
	 *            nazwa pola
	 * @param value
	 *            wartość pola
	 */
	public void set(String name, Object value) {
		// TODO zastanowić się czy implementować
	}

	/**
	 * @return the {@link #caseProperties}
	 */
	public List<CcrtoProperty> getCaseProperties() {
		List<CcrtoProperty> result = new ArrayList<>();
		for (Object entry : anyProperties) {
			if (entry instanceof JAXBElement) {
				@SuppressWarnings("unchecked")
				JAXBElement<CcrtoProperty> e = (JAXBElement<CcrtoProperty>) entry;
				CcrtoProperty property = e.getValue();
				property.setPropertyName(e.getName().getLocalPart());
				result.add(property);
			}
		}
		return result;
	}

	/* Overridden (non-Javadoc) */
	public int size() {
		return getAnyProperties().size();
	}

	/* Overridden (non-Javadoc) */
	public boolean isEmpty() {
		return getAnyProperties().isEmpty();
	}

	/* Overridden (non-Javadoc) */
	public boolean contains(CcrtoProperty o) {
		return getCaseProperties().contains(o);
	}

	/* Overridden (non-Javadoc) */
	public boolean add(CcrtoProperty e) {
		final String schemaName = ObjectFactory.XML_SCHEMA;
		if (StringUtils.isBlank(e.propertyName)) {
			throw new IllegalArgumentException("Property name is required!");
		}
		if (withRequiredPosition != null && withRequiredPosition) {
			QName attrName = ObjectFactory.getQName(schemaName, ObjectFactory.ATTR_OBJECT_WITH_REQUIRED_POSITION);
			if (e.getOtherAttributes().get(attrName) == null) {
				throw new IllegalArgumentException(String.format("Attribute %s is required!", attrName));
			}
		}
		QName elementName = ObjectFactory.getQName(schemaName, e.propertyName);
		JAXBElement<CcrtoProperty> element = new JAXBElement<>(elementName, CcrtoProperty.class, e);
		return getAnyProperties().add(element);
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
		result = prime * result + ((getCaseProperties() == null) ? 0 : getCaseProperties().hashCode());
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
		if (getCaseProperties() == null) {
			if (other.getCaseProperties() != null) {
				return false;
			}
		} else if (!getCaseProperties().equals(other.getCaseProperties())) {
			return false;
		}
		return status != other.status;
	}

	/**
	 * @return the {@link #anyProperties}
	 */
	public List<JAXBElement<CcrtoProperty>> getAnyProperties() {
		if (anyProperties == null) {
			anyProperties = new ArrayList<>();
		}
		return anyProperties;
	}

	/**
	 * @return the {@link #withRequiredPosition}
	 */
	public Boolean getWithRequiredPosition() {
		return withRequiredPosition;
	}

	/**
	 * @param withRequiredPosition
	 *            the {@link #withRequiredPosition} to set
	 */
	public void setWithRequiredPosition(Boolean withRequiredPosition) {
		this.withRequiredPosition = withRequiredPosition;
	}

}
