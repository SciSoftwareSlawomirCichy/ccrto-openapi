package org.ccrto.openapi.caseheader;

import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.refs.SubCaseReferenceRef;

public class SubCaseReference implements SubCaseReferenceRef {

	private static final long serialVersionUID = 6347433243007687435L;

	@XmlElement(required = true, nillable = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private String referenceName;

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
	 * @return the {@link #referenceName}
	 */
	public String getReferenceName() {
		return referenceName;
	}

	/**
	 * @param referenceName
	 *            the {@link #referenceName} to set
	 */
	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

}
