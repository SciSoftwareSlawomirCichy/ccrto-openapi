package org.ccrto.openapi.core;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.internal.IValueLob;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "LOB")
public class CcrtoPropertyLob extends CcrtoProperty implements IValueLob {

	private static final long serialVersionUID = 6818579847092109946L;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute(required = false)
	private Boolean isEncoded;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute
	private KnownLobMetadata metadataInfo;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute
	private String mimeType;

	@XmlAnyElement(lax = true)
	protected List<Object> value;

	@Override
	public boolean isNull() {
		return StringUtils.isBlank(this.propertyValue);
	}

	/**
	 * @return the {@link #metadataInfo}
	 */
	public KnownLobMetadata getMetadataInfo() {
		return metadataInfo;
	}

	/**
	 * @param metadataInfo
	 *            the {@link #metadataInfo} to set
	 */
	public void setMetadataInfo(KnownLobMetadata metadataInfo) {
		this.metadataInfo = metadataInfo;
	}

	/**
	 * @return the {@link #mimeType}
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType
	 *            the {@link #mimeType} to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the {@link #isEncoded}
	 */
	public Boolean getIsEncoded() {
		return isEncoded;
	}

	/**
	 * @param isEncoded
	 *            the {@link #isEncoded} to set
	 */
	public void setIsEncoded(Boolean isEncoded) {
		this.isEncoded = isEncoded;
	}

	/**
	 * @return the {@link #value}
	 */
	public List<Object> getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void setValue(List<Object> value) {
		this.value = value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void addValue(Object value) {
		if (this.value == null) {
			this.value = new ArrayList<>();
		}
		this.value.add(value);
	}

}
