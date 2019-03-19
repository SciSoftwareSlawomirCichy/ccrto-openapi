package org.ccrto.openapi.caseheader;

import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.refs.StatusRef;

public class CaseStatus implements StatusRef {

	private static final long serialVersionUID = 3982929366352435581L;

	@XmlElement(required = true, nillable = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private String status;

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
	 * @return the {@link #status}
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
