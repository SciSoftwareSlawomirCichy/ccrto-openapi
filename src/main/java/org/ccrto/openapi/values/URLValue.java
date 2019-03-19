package org.ccrto.openapi.values;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pro.ibpm.mercury.attrs.html.HtmlSupportedUrl;
import pro.ibpm.mercury.attrs.javax.api.IURLValue;

/**
 * 
 * FileReferenceValue obiekt reprezentujący URL do pliku, strony.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "URL", namespace = "http://pro.ibpm.mercury.attrs")
public class URLValue implements Serializable, IURLValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7418870764478088106L;
	public static final String JSON_TITLE_PROP = "title";
	public static final String JSON_TYPE_PROP = "type";
	public static final String JSON_REFERENCE_PROP = "urlReference";

	/** Tytuł pliku */
	@XmlElement(required = true, name = "title", namespace = "http://pro.ibpm.mercury.attrs")
	private String title;

	/**
	 * typ referencji do pliku. Określany jako wartości {@link HtmlSupportedUrl}.
	 * Zadaniem pola jest danie możliwości dodania ikonki przy linku reprezentującej
	 * dokument docelowy.
	 */
	@XmlElement(required = false, name = "type", namespace = "http://pro.ibpm.mercury.attrs", defaultValue = "URL")
	private String type = HtmlSupportedUrl.URL.name();

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
	@XmlElement(required = true, name = "urlReference", namespace = "http://pro.ibpm.mercury.attrs")
	private String urlReference;

	public URLValue() {
		super();
	}

	public URLValue(String title, String urlReference) {
		super();
		this.title = title;
		this.urlReference = urlReference;
	}

	public URLValue(String title, String urlReference, String type) {
		super();
		this.title = title;
		this.urlReference = urlReference;
		this.type = type;
	}

	public URLValue(String title, String urlReference, HtmlSupportedUrl type) {
		super();
		this.title = title;
		this.urlReference = urlReference;
		this.type = type.name();
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
	public String getType() {
		return type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public void setType(String type) {
		this.type = type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String toString() {
		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(JSON_TITLE_PROP, title);
		jsonObject.put(JSON_TYPE_PROP, type);
		jsonObject.put(JSON_REFERENCE_PROP, urlReference);
		return jsonObject.toString();
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((urlReference == null) ? 0 : urlReference.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		URLValue other = (URLValue) obj;
		if (urlReference == null) {
			if (other.urlReference != null) {
				return false;
			}
		} else if (!urlReference.equals(other.urlReference)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

}
