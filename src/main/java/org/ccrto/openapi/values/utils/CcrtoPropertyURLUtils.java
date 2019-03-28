package org.ccrto.openapi.values.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.values.CcrtoPropertyNameValuePair;
import org.ccrto.openapi.values.CcrtoPropertyType;
import org.ccrto.openapi.values.CcrtoPropertyURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pl.slawas.entities.NameValuePair;

/**
 * 
 * CcrtoPropertyURLUtils
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CcrtoPropertyURLUtils {

	private static final Logger logger = LoggerFactory.getLogger(CcrtoPropertyURLUtils.class);

	private CcrtoPropertyURLUtils() {
	}

	public static NameValuePair getValue(Context context, String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObject = mapper.readTree(key);
			String title = jsonObject.get(CcrtoPropertyURL.PROPERTY_TITLE).textValue();
			String value = jsonObject.get(CcrtoPropertyURL.PROPERTY_TYPE).textValue();
			return CcrtoPropertyNameValuePair.getInstance(title, value);
		} catch (Exception e) {
			return CcrtoPropertyNameValuePair.getInstance(key, key);
		}
	}

	public static CcrtoPropertyURL getUrlValue(Context context, String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObject = mapper.readTree(key);
			String title = jsonObject.get(CcrtoPropertyURL.PROPERTY_TITLE).textValue();
			String type = jsonObject.get(CcrtoPropertyURL.PROPERTY_TYPE).textValue();
			String referenceId = jsonObject.get(CcrtoPropertyURL.PROPERTY_REFERENCE).textValue();
			return new CcrtoPropertyURL(title, referenceId, type);
		} catch (Exception e) {
			logger.error(String.format("--->getUrlValue: key: '%s'", key), e);
			return null;
		}
	}

	public static String encode2String(Context context, String presentationValue) {
		if (StringUtils.isBlank(presentationValue)) {
			return null;
		}
		ObjectNode jsonObject = encode2Json(context, presentationValue);
		if (jsonObject != null) {
			return jsonObject.toString();
		}
		return StringUtils.EMPTY;
	}

	public static CcrtoPropertyURL encode2Value(Context context, String presentationValue) {
		if (StringUtils.isBlank(presentationValue)) {
			return null;
		}
		ObjectNode jsonObject = encode2Json(context, presentationValue);
		if (jsonObject != null) {
			String title = jsonObject.get(CcrtoPropertyURL.PROPERTY_TITLE).textValue();
			String type = jsonObject.get(CcrtoPropertyURL.PROPERTY_TYPE).textValue();
			String referenceId = jsonObject.get(CcrtoPropertyURL.PROPERTY_REFERENCE).textValue();
			return new CcrtoPropertyURL(title, referenceId, type);
		}
		return null;
	}

	private static ObjectNode encode2Json(Context context, String presentationValue) {
		if (StringUtils.isBlank(presentationValue)) {
			return null;
		}
		ObjectNode jsonObject;
		boolean hasTitle = false;
		boolean hasType = false;
		boolean hasValue = false;
		try {
			ObjectMapper mapper = new ObjectMapper();
			jsonObject = (ObjectNode) mapper.readTree(presentationValue);
		} catch (Exception e) {
			/* nie udało się utworzyć obiektu JSON? */
			jsonObject = JsonNodeFactory.instance.objectNode();
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TITLE, StringUtils.EMPTY);
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TYPE, CcrtoPropertyType.URL.name());
			jsonObject.put(CcrtoPropertyURL.PROPERTY_REFERENCE, presentationValue);
		}

		if (jsonObject.has(CcrtoPropertyURL.PROPERTY_TITLE)) {
			hasTitle = true;
		}
		if (jsonObject.has(CcrtoPropertyURL.PROPERTY_TYPE)) {
			hasType = true;
		}
		if (jsonObject.has(CcrtoPropertyURL.PROPERTY_REFERENCE)) {
			hasValue = true;
		}
		if (hasTitle && hasValue && hasType) {
			/* wszystko jest ustawione - super! */
			return jsonObject;
		} else if (hasTitle && hasValue) {
			/* nie ma ustawionego typu - nic nie szkodzi - ustawie domyślny */
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TYPE, CcrtoPropertyType.URL.name());
			return jsonObject;
		} else if (hasType && hasValue) {
			/* nie ma ustawionego tytułu - nic nie szkodzi - ustawie domyślny */
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TITLE, StringUtils.EMPTY);
			return jsonObject;
		} else if (hasValue) {
			/* ma ustawione tylko referenceId - resztę ustawię domyślne */
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TYPE, CcrtoPropertyType.URL.name());
			jsonObject.put(CcrtoPropertyURL.PROPERTY_TITLE, StringUtils.EMPTY);
			return jsonObject;
		}
		return null;
	}

	/**
	 * Pobieranie nazwy hosta z wartości URL'a
	 * 
	 * @param urlValue
	 *            obiekt reprezentujący wartość URL
	 * @return zidentyfikowana nazwa hosta albo {@code null} gdy identyfikacja jest
	 *         niemożliwa.
	 */
	public static String getHostName(CcrtoPropertyURL urlValue) {
		if (urlValue == null) {
			return null;
		}
		String urlReference = null;
		try {
			urlReference = urlValue.getUrlReference();
			if (StringUtils.isNotBlank(urlReference) && urlReference.toLowerCase().startsWith("http")) {
				/* wyciągam nazwę hosta, tak jak obiecałem */
				URL url = new URL(urlReference);
				return url.getHost();
			}
		} catch (MalformedURLException e) {
			if (logger.isDebugEnabled()) {
				logger.warn(String.format("URL encoding error for '%s'", urlReference), e);
			}
		}
		return urlReference;
	}
}
