package org.ccrto.openapi.caseheader;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.ccrto.openapi.context.UserContext;
import org.ccrto.openapi.context.UserRoleContext;
import org.ccrto.openapi.refs.CaseRef;
import org.ccrto.openapi.values.DateValue;
import org.ccrto.openapi.values.json.DateValueDeserializer;
import org.ccrto.openapi.values.json.DateValueSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlAccessorType(XmlAccessType.NONE)
public class CaseHeader implements CaseRef {

	private static final long serialVersionUID = -443334392732502658L;

	@XmlElement(required = false)
	private String id;

	@XmlElement(required = false)
	private String href;

	@XmlElement(required = false)
	private String inventoryCode;

	@XmlElement(required = true)
	private CaseStatus status;

	@XmlElement(required = false)
	private Long previousVersionId;

	@XmlElement(required = false)
	private Double priceValue;

	@XmlElement(required = false)
	private String priceValueCode;

	@XmlElement(required = false)
	@JsonSerialize(using = DateValueSerializer.class)
	@JsonDeserialize(using = DateValueDeserializer.class)
	private DateValue priceExchangeDate;

	@XmlElement(required = false)
	@JsonSerialize(using = DateValueSerializer.class)
	@JsonDeserialize(using = DateValueDeserializer.class)
	private DateValue createDate;

	/** nazwa użytkownika tworzącego {@link SystemUser#getName() */
	@XmlElement(required = false)
	private UserContext createdBy;

	@XmlElement(required = false)
	private UserRoleContext createdByRole;

	@XmlElement(required = false)
	@JsonSerialize(using = DateValueSerializer.class)
	@JsonDeserialize(using = DateValueDeserializer.class)
	private DateValue lastModifyDate;

	/** nazwa użytkownika modyfikującego {@link SystemUser#getName() */
	@XmlElement(required = false)
	private UserContext lastModifiedBy;

	@XmlElement(required = false)
	private UserRoleContext lastModifiedByRole;

	@XmlElement(required = false)
	private String modifyComment;

	@XmlElement(required = true)
	private CaseType type;

	@XmlElement(required = false)
	private CaseGroup group;

	@XmlElement(required = false)
	private CaseStore store;

	@XmlElement(required = false)
	private Set<CaseProcess> bpmProcesses;

	@XmlElement(required = false)
	private SubCaseReference subCaseReferenceId;

	@XmlElement(required = false)
	private Boolean dirty;

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
	 * @return the {@link #inventoryCode}
	 */
	public String getInventoryCode() {
		return inventoryCode;
	}

	/**
	 * @param inventoryCode
	 *            the {@link #inventoryCode} to set
	 */
	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}

	/**
	 * @return the {@link #status}
	 */
	public CaseStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	public void setStatus(CaseStatus status) {
		this.status = status;
	}

	/**
	 * @return the {@link #previousVersionId}
	 */
	public Long getPreviousVersionId() {
		return previousVersionId;
	}

	/**
	 * @param previousVersionId
	 *            the {@link #previousVersionId} to set
	 */
	public void setPreviousVersionId(Long previousVersionId) {
		this.previousVersionId = previousVersionId;
	}

	/**
	 * @return the {@link #priceValue}
	 */
	public Double getPriceValue() {
		return priceValue;
	}

	/**
	 * @param priceValue
	 *            the {@link #priceValue} to set
	 */
	public void setPriceValue(Double priceValue) {
		this.priceValue = priceValue;
	}

	/**
	 * @return the {@link #priceValueCode}
	 */
	public String getPriceValueCode() {
		return priceValueCode;
	}

	/**
	 * @param priceValueCode
	 *            the {@link #priceValueCode} to set
	 */
	public void setPriceValueCode(String priceValueCode) {
		this.priceValueCode = priceValueCode;
	}

	/**
	 * @return the {@link #priceExchangeDate}
	 */
	public DateValue getPriceExchangeDate() {
		return priceExchangeDate;
	}

	/**
	 * @param priceExchangeDate
	 *            the {@link #priceExchangeDate} to set
	 */
	public void setPriceExchangeDate(DateValue priceExchangeDate) {
		this.priceExchangeDate = priceExchangeDate;
	}

	/**
	 * @return the {@link #createDate}
	 */
	public DateValue getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the {@link #createDate} to set
	 */
	public void setCreateDate(DateValue createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the {@link #createdBy}
	 */
	public UserContext getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the {@link #createdBy} to set
	 */
	public void setCreatedBy(UserContext createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the {@link #createdByRole}
	 */
	public UserRoleContext getCreatedByRole() {
		return createdByRole;
	}

	/**
	 * @param createdByRole
	 *            the {@link #createdByRole} to set
	 */
	public void setCreatedByRole(UserRoleContext createdByRole) {
		this.createdByRole = createdByRole;
	}

	/**
	 * @return the {@link #lastModifyDate}
	 */
	public DateValue getLastModifyDate() {
		return lastModifyDate;
	}

	/**
	 * @param lastModifyDate
	 *            the {@link #lastModifyDate} to set
	 */
	public void setLastModifyDate(DateValue lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	/**
	 * @return the {@link #lastModifiedBy}
	 */
	public UserContext getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the {@link #lastModifiedBy} to set
	 */
	public void setLastModifiedBy(UserContext lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the {@link #lastModifiedByRole}
	 */
	public UserRoleContext getLastModifiedByRole() {
		return lastModifiedByRole;
	}

	/**
	 * @param lastModifiedByRole
	 *            the {@link #lastModifiedByRole} to set
	 */
	public void setLastModifiedByRole(UserRoleContext lastModifiedByRole) {
		this.lastModifiedByRole = lastModifiedByRole;
	}

	/**
	 * @return the {@link #modifyComment}
	 */
	public String getModifyComment() {
		return modifyComment;
	}

	/**
	 * @param modifyComment
	 *            the {@link #modifyComment} to set
	 */
	public void setModifyComment(String modifyComment) {
		this.modifyComment = modifyComment;
	}

	/**
	 * @return the {@link #type}
	 */
	public CaseType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the {@link #type} to set
	 */
	public void setType(CaseType type) {
		this.type = type;
	}

	/**
	 * @return the {@link #group}
	 */
	public CaseGroup getGroup() {
		return group;
	}

	/**
	 * @param group
	 *            the {@link #group} to set
	 */
	public void setGroup(CaseGroup group) {
		this.group = group;
	}

	/**
	 * @return the {@link #store}
	 */
	public CaseStore getStore() {
		return store;
	}

	/**
	 * @param store
	 *            the {@link #store} to set
	 */
	public void setStore(CaseStore store) {
		this.store = store;
	}

	/**
	 * @return the {@link #bpmProcesses}
	 */
	public Set<CaseProcess> getBpmProcesses() {
		return bpmProcesses;
	}

	/**
	 * @param bpmProcesses
	 *            the {@link #bpmProcesses} to set
	 */
	public void setBpmProcesses(Set<CaseProcess> bpmProcesses) {
		this.bpmProcesses = bpmProcesses;
	}

	/**
	 * @return the {@link #subCaseReferenceId}
	 */
	public SubCaseReference getSubCaseReferenceId() {
		return subCaseReferenceId;
	}

	/**
	 * @param subCaseReferenceId
	 *            the {@link #subCaseReferenceId} to set
	 */
	public void setSubCaseReferenceId(SubCaseReference subCaseReferenceId) {
		this.subCaseReferenceId = subCaseReferenceId;
	}

	/**
	 * @return the {@link #dirty}
	 */
	public Boolean getDirty() {
		return dirty;
	}

	/**
	 * @param dirty
	 *            the {@link #dirty} to set
	 */
	public void setDirty(Boolean dirty) {
		this.dirty = dirty;
	}

}
