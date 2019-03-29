package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.core.internal.IValueURL;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * CcrtoValueURL
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "URL")
public class CcrtoPropertyURL extends CcrtoProperty implements IValueURL {

	private static final long serialVersionUID = -7760389941872907086L;
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_TYPE = "urlClass";
	public static final String PROPERTY_REFERENCE = "urlReference";

	/** Tytuł pliku/linku */
	@XmlElement(required = true, name = "title")
	private String title;

	/**
	 * typ referencji do pliku. Określany jako wartości {@link CcrtoUrlClass}.
	 * Zadaniem pola jest danie możliwości dodania ikonki przy linku reprezentującej
	 * dokument docelowy.
	 */
	@XmlElement(required = false, name = "urlType")
	private CcrtoUrlClass urlClass = CcrtoUrlClass.LINK;

	/**
	 * Referencja do pliku. Może być reprezentowana przez wartości:
	 * <ul>
	 * <li>URL: w postaci: "[[http|https]://][host][:port][/]&lt;ścieżka></li>
	 * <li>Data URI: w postaci:
	 * "data:[mimetype];base64,&lt;zakodowany_plik_w_base64></li>
	 * <li>Data URI: w postaci:
	 * "data:[mimetype];base64,${LOB:<identyfikator_LOB_w_bazie_danych}</li>
	 * </ul>
	 */
	@XmlElement(required = true, name = "urlReference")
	private String urlReference;

	public CcrtoPropertyURL() {
		super();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String getTitle() {
		return title;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String getUrlReference() {
		return urlReference;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setUrlReference(String urlReference) {
		this.urlReference = urlReference;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setType(String type) {
		CcrtoPropertyType fieldType = CcrtoPropertyType.getType(type);
		if (fieldType == null || !CcrtoPropertyType.URL.equals(fieldType)) {
			throw new IllegalArgumentException(
					String.format("Type should be value: \"%s\"", CcrtoPropertyType.URL.getName()));
		}
		this.type = type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String toString() {
		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(PROPERTY_TITLE, title);
		jsonObject.put(PROPERTY_TYPE, urlClass.getName());
		jsonObject.put(PROPERTY_REFERENCE, urlReference);
		return jsonObject.toString();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((urlClass == null) ? 0 : urlClass.hashCode());
		result = prime * result + ((urlReference == null) ? 0 : urlReference.hashCode());
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
		if (!(obj instanceof CcrtoPropertyURL)) {
			return false;
		}
		CcrtoPropertyURL other = (CcrtoPropertyURL) obj;
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (urlClass == null) {
			if (other.urlClass != null) {
				return false;
			}
		} else if (!urlClass.equals(other.urlClass)) {
			return false;
		}
		if (urlReference == null) {
			if (other.urlReference != null) {
				return false;
			}
		} else if (!urlReference.equals(other.urlReference)) {
			return false;
		}
		return true;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return false;
	}

	public static CcrtoPropertyURL getInstance(String title, String urlReference) {
		CcrtoPropertyURL instance = new CcrtoPropertyURL();
		instance.title = title;
		instance.urlReference = urlReference;
		return instance;
	}

	public static CcrtoPropertyURL getInstance(String title, String urlReference, String urlClass) {
		CcrtoPropertyURL instance = getInstance(title, urlReference);
		instance.urlClass = CcrtoUrlClass.getClass(urlClass);
		return instance;
	}

	public static CcrtoPropertyURL getInstance(String title, String urlReference, CcrtoUrlClass urlClass) {
		CcrtoPropertyURL instance = getInstance(title, urlReference);
		instance.urlClass = urlClass;
		return instance;
	}
}
