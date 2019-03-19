package org.ccrto.openapi.caseheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.refs.CaseGroupRef;

@XmlAccessorType(XmlAccessType.NONE)
public class CaseGroup implements CaseGroupRef {

	private static final long serialVersionUID = -8425795373256319488L;

	@XmlElement(required = true, nillable = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private String group;

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
	 * @return the {@link #group}
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the {@link #group} to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

}
