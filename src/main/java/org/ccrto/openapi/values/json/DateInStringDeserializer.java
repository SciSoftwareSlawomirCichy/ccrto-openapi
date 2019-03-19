package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.javax.DateInString;

@Component
public class DateInStringDeserializer extends JsonDeserializer<DateInString> {

	@Override
	public DateInString deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode node = p.getCodec().readTree(p);
		DateInString dateInString = new DateInString();
		if (node.isTextual()) {
			String value = node.textValue();
			dateInString.setEncoded(false);
			dateInString.setString(value);
		} else {
			Long value = node.longValue();
			dateInString.setEncoded(true);
			dateInString.setString(Long.toString(value));
		}

		return dateInString;
	}

}
