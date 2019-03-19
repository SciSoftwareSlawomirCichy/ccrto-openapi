package org.ccrto.openapi.values.json;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonDeserializerException extends JsonProcessingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4606062535297624737L;

	public JsonDeserializerException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}

}
