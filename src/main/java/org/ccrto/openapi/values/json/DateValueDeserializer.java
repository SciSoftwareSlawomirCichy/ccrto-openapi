package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.ccrto.openapi.values.DateValue;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class DateValueDeserializer extends JsonDeserializer<DateValue> {

	@Override
	public DateValue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		DateValue dateInString = new DateValue();
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
