package org.ccrto.openapi.values.json;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * JsonDeserializerException
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class JsonDeserializerException extends JsonProcessingException {

	private static final long serialVersionUID = -676870512123001796L;

	public JsonDeserializerException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}

}
