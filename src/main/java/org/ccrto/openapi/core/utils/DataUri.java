package org.ccrto.openapi.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * DataUri - obiekt pomocniczy wykorzystywany do identyfikacji plików.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class DataUri {

	/**
	 * From the RFC 4648: Base encoding of data is used in many situations to store
	 * or transfer data in environments that, perhaps for legacy reasons, are
	 * restricted to US-ASCII data.
	 */
	public static final String BASE_64_REGEXP_STRING = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$";
	public static final Pattern BASE_64_PATTERN = Pattern.compile(BASE_64_REGEXP_STRING);
	public static final Pattern DATA_URI_PATTERN = Pattern.compile("^data:(.*?);base64,(.*)", Pattern.CASE_INSENSITIVE);

	/** dane zakodowane w postaci base64 */
	private String data;
	/** mime type kontentu danych */
	private final String contentType;

	public DataUri(String data, String contentType) {
		super();
		this.data = data;
		this.contentType = contentType;
	}

	/**
	 * @return the {@link #data}
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data
	 *            the {@link #data} to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the {@link #contentType}
	 */
	public String getContentType() {
		return contentType;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("data:").append(contentType).append(";base64,").append(data);
		return sb.toString();
	}

	/**
	 * Sprawdzenie czy wartość reprezentuje treść zakodowaną w base64 reprezentującą
	 * kontent na podstawie definicji RFC 2397 (Data URI scheme)
	 * https://en.wikipedia.org/wiki/Data_URI_scheme#Syntax
	 * 
	 * @param value
	 *            wartość
	 * @return niepusty obiekt jeżeli wartość nie jest pusta oraz gdy spełnia
	 *         warunki (Data URI scheme)
	 */
	public static DataUri checkDataUriValue(String value) {
		if (StringUtils.isBlank(value)) {
			return null;
		}
		Matcher m = DATA_URI_PATTERN.matcher(value);
		if (!m.find()) {
			return null;
		}
		String contentType = m.group(1);
		String data = m.group(2);
		return new DataUri(data, contentType);
	}

}
