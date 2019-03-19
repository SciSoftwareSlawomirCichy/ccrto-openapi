package org.ccrto.openapi.values.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pl.slawas.entities.NameValuePair;
import pro.ibpm.mercury.attrs.KnownLobMetadata;
import pro.ibpm.mercury.attrs.javax.URLValue;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.entities.beans.NameValuePairBean;

public class URLValueUtils {

	private static final Logger logger = LoggerFactory.getLogger(URLValueUtils.class);

	private URLValueUtils() {
	}

	public static NameValuePair getValue(Context context, String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObject = mapper.readTree(key);
			String title = jsonObject.get(URLValue.JSON_TITLE_PROP).textValue();
			String value = jsonObject.get(URLValue.JSON_REFERENCE_PROP).textValue();
			return new NameValuePairBean(title, value);
		} catch (Exception e) {
			return new NameValuePairBean(key, key);
		}
	}

	public static URLValue getUrlValue(Context context, String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonObject = mapper.readTree(key);
			String title = jsonObject.get(URLValue.JSON_TITLE_PROP).textValue();
			String type = jsonObject.get(URLValue.JSON_TYPE_PROP).textValue();
			String referenceId = jsonObject.get(URLValue.JSON_REFERENCE_PROP).textValue();
			return new URLValue(title, referenceId, type);
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

	public static URLValue encode2Value(Context context, String presentationValue) {
		if (StringUtils.isBlank(presentationValue)) {
			return null;
		}
		ObjectNode jsonObject = encode2Json(context, presentationValue);
		if (jsonObject != null) {
			String title = jsonObject.get(URLValue.JSON_TITLE_PROP).textValue();
			String type = jsonObject.get(URLValue.JSON_TYPE_PROP).textValue();
			String referenceId = jsonObject.get(URLValue.JSON_REFERENCE_PROP).textValue();
			return new URLValue(title, referenceId, type);
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
			jsonObject.put(URLValue.JSON_TITLE_PROP, StringUtils.EMPTY);
			jsonObject.put(URLValue.JSON_TYPE_PROP, KnownLobMetadata.ANY.name());
			jsonObject.put(URLValue.JSON_REFERENCE_PROP, presentationValue);
		}

		if (jsonObject.has(URLValue.JSON_TITLE_PROP)) {
			hasTitle = true;
		}
		if (jsonObject.has(URLValue.JSON_TYPE_PROP)) {
			hasType = true;
		}
		if (jsonObject.has(URLValue.JSON_REFERENCE_PROP)) {
			hasValue = true;
		}
		if (hasTitle && hasValue && hasType) {
			/* wszystko jest ustawione - super! */
			return jsonObject;
		} else if (hasTitle && hasValue) {
			/* nie ma ustawionego typu - nic nie szkodzi - ustawie domyślny */
			jsonObject.put(URLValue.JSON_TYPE_PROP, KnownLobMetadata.ANY.name());
			return jsonObject;
		} else if (hasType && hasValue) {
			/* nie ma ustawionego tytułu - nic nie szkodzi - ustawie domyślny */
			jsonObject.put(URLValue.JSON_TITLE_PROP, StringUtils.EMPTY);
			return jsonObject;
		} else if (hasValue) {
			/* ma ustawione tylko referenceId - resztę ustawię domyślne */
			jsonObject.put(URLValue.JSON_TYPE_PROP, KnownLobMetadata.ANY.name());
			jsonObject.put(URLValue.JSON_TITLE_PROP, StringUtils.EMPTY);
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
	public static String getHostName(URLValue urlValue) {
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
