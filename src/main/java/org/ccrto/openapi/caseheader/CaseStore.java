package org.ccrto.openapi.caseheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.refs.CaseStoreRef;

@XmlAccessorType(XmlAccessType.NONE)
public class CaseStore implements CaseStoreRef {

	private static final long serialVersionUID = -1795294062939436053L;

	@XmlElement(required = true, nillable = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private Integer storeCount;

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
	 * @return the {@link #storeCount}
	 */
	public Integer getStoreCount() {
		return storeCount;
	}

	/**
	 * @param storeCount
	 *            the {@link #storeCount} to set
	 */
	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}

}
