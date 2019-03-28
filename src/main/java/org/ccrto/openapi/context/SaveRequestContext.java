package org.ccrto.openapi.context;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ccrto.openapi.caseheader.CaseType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * SaveContext parametry kontekstu definiowane podczas wprowadzania zmiany do
 * systemu.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SaveRequestContext implements Serializable {

	private static final long serialVersionUID = -8104067213080716088L;

	/** Komentarz użytkownika (dlaczego wykonuje daną akcję) */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private String modifyComment;

	/** Nazwa typu/identyfikator typu pod jakim ma zostać zapisana sprawa */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private CaseType saveCaseAsType;

	/**
	 * Czy jeżeli pola nie istnieją w żądaniu zapisu sprawy (nie zostały przesłane
	 * pola obiektu), to ustawiać je na wartość {@code null} ?
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false, defaultValue = "false")
	@XmlElement(required = false, defaultValue = "false")
	private Boolean valueIsNullIfFieldNotExisisInRequest = false;

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
	 * @return the {@link #saveCaseAsType}
	 */
	public CaseType getSaveCaseAsType() {
		return saveCaseAsType;
	}

	/**
	 * @param saveCaseAsType
	 *            the {@link #saveCaseAsType} to set
	 */
	public void setSaveCaseAsType(CaseType saveCaseAsType) {
		this.saveCaseAsType = saveCaseAsType;
	}

	/**
	 * @return the {@link #valueIsNullIfFieldNotExisisInRequest}
	 */
	public Boolean getValueIsNullIfFieldNotExisisInRequest() {
		return valueIsNullIfFieldNotExisisInRequest;
	}

	/**
	 * @param valueIsNullIfFieldNotExisisInRequest
	 *            the {@link #valueIsNullIfFieldNotExisisInRequest} to set
	 */
	public void setValueIsNullIfFieldNotExisisInRequest(Boolean valueIsNullIfFieldNotExisisInRequest) {
		this.valueIsNullIfFieldNotExisisInRequest = valueIsNullIfFieldNotExisisInRequest;
	}

	/**
	 * Tworzenie kopii obecnej instancji obiektu
	 * 
	 * @return kopia instancji
	 */
	public SaveRequestContext copy() {
		SaveRequestContext copy = new SaveRequestContext();
		copy.modifyComment = this.modifyComment;
		copy.saveCaseAsType = this.saveCaseAsType;
		copy.valueIsNullIfFieldNotExisisInRequest = this.valueIsNullIfFieldNotExisisInRequest;
		return copy;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((modifyComment == null) ? 0 : modifyComment.hashCode());
		result = prime * result + ((saveCaseAsType == null) ? 0 : saveCaseAsType.hashCode());
		result = prime * result + ((valueIsNullIfFieldNotExisisInRequest == null) ? 0
				: valueIsNullIfFieldNotExisisInRequest.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SaveRequestContext)) {
			return false;
		}
		SaveRequestContext other = (SaveRequestContext) obj;
		if (modifyComment == null) {
			if (other.modifyComment != null) {
				return false;
			}
		} else if (!modifyComment.equals(other.modifyComment)) {
			return false;
		}
		if (saveCaseAsType == null) {
			if (other.saveCaseAsType != null) {
				return false;
			}
		} else if (!saveCaseAsType.equals(other.saveCaseAsType)) {
			return false;
		}
		if (valueIsNullIfFieldNotExisisInRequest == null) {
			if (other.valueIsNullIfFieldNotExisisInRequest != null) {
				return false;
			}
		} else if (!valueIsNullIfFieldNotExisisInRequest.equals(other.valueIsNullIfFieldNotExisisInRequest)) {
			return false;
		}
		return true;
	}

}
