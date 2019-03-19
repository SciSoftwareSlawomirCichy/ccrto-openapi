package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.javax.DateInMillis;

@Component
public class DateInMillisDeserializer extends JsonDeserializer<DateInMillis> {

	@Override
	public DateInMillis deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode node = p.getCodec().readTree(p);
		DateInMillis dateInString = new DateInMillis();
		if (node.isTextual()) {
			String value = node.textValue();
			dateInString.setMillis(Long.parseLong(value));
		} else {
			Long value = node.longValue();
			dateInString.setMillis(value);
		}

		return dateInString;
	}

}
