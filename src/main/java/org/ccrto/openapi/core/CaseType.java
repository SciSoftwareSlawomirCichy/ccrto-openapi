package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.core.refs.CaseTypeRef;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlAccessorType(XmlAccessType.NONE)
public class CaseType implements CaseTypeRef {

	private static final long serialVersionUID = -2148764914238324081L;

	@JsonProperty(required = true)
	@XmlElement(required = true, nillable = false)
	private String id;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String href;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String type;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String objectID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String rootVersionContextID;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String version;

	/**
	 * Nazwy parametrów, która reprezentują unikalny klucz główny pośród typów
	 * spraw. Np. Zbieramy sprawy typu "User" i można ustawić, że unikalną wartością
	 * tych obiektów jest property o nazwie "userName".
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String pkPropertyNames;

	/**
	 * @return the {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the {@link #id} to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the {@link #href}
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the {@link #href} to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the {@link #type} to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the {@link #objectID}
	 */
	public String getObjectID() {
		return objectID;
	}

	/**
	 * @param objectID
	 *            the {@link #objectID} to set
	 */
	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	/**
	 * @return the {@link #rootVersionContextID}
	 */
	public String getRootVersionContextID() {
		return rootVersionContextID;
	}

	/**
	 * @param rootVersionContextID
	 *            the {@link #rootVersionContextID} to set
	 */
	public void setRootVersionContextID(String rootVersionContextID) {
		this.rootVersionContextID = rootVersionContextID;
	}

	/**
	 * @return the {@link #version}
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the {@link #version} to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the {@link #pkPropertyNames}
	 */
	public String getPkPropertyNames() {
		return pkPropertyNames;
	}

	/**
	 * @param pkPropertyNames
	 *            the {@link #pkPropertyNames} to set
	 */
	public void setPkPropertyNames(String pkPropertyNames) {
		this.pkPropertyNames = pkPropertyNames;
	}

}
