package org.ccrto.openapi.caseheader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.refs.CaseProcessRef;
import org.ccrto.openapi.values.DateValue;
import org.ccrto.openapi.values.json.DateValueDeserializer;
import org.ccrto.openapi.values.json.DateValueSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlAccessorType(XmlAccessType.NONE)
public class CaseProcess implements CaseProcessRef {

	private static final long serialVersionUID = 3510762390564134184L;

	@XmlElement(required = true, nillable = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private String process;

	@XmlElement(required = true)
	private CaseProcessStatus status;

	@XmlElement(required = false)
	@JsonSerialize(using = DateValueSerializer.class)
	@JsonDeserialize(using = DateValueDeserializer.class)
	private DateValue endDate;

	@XmlElement(required = false)
	@JsonSerialize(using = DateValueSerializer.class)
	@JsonDeserialize(using = DateValueDeserializer.class)
	private DateValue dueDate;

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
	 * @return the {@link #process}
	 */
	public String getProcess() {
		return process;
	}

	/**
	 * @param process
	 *            the {@link #process} to set
	 */
	public void setProcess(String process) {
		this.process = process;
	}

	/**
	 * @return the {@link #status}
	 */
	public CaseProcessStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	public void setStatus(CaseProcessStatus status) {
		this.status = status;
	}

	/**
	 * @return the {@link #endDate}
	 */
	public DateValue getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the {@link #endDate} to set
	 */
	public void setEndDate(DateValue endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the {@link #dueDate}
	 */
	public DateValue getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the {@link #dueDate} to set
	 */
	public void setDueDate(DateValue dueDate) {
		this.dueDate = dueDate;
	}

}
