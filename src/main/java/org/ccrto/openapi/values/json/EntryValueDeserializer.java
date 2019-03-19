package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.javax.EntryValue;

@Component
public class EntryValueDeserializer extends JsonDeserializer<EntryValue> {

	@Override
	public EntryValue deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode node = p.getCodec().readTree(p);
		String pKey = null;
		if (node.has(EntryValue.PROPERTY_KEY)) {
			JsonNode key = node.get(EntryValue.PROPERTY_KEY);
			if (key.isTextual()) {
				pKey = key.textValue();
			} else {
				pKey = key.asText();
			}
		}
		String pValue = null;
		if (node.has(EntryValue.PROPERTY_VALUE)) {
			JsonNode value = node.get(EntryValue.PROPERTY_VALUE);
			if (value.isTextual()) {
				pValue = value.textValue();
			} else {
				pValue = value.asText();
			}
		}
		return new EntryValue(pKey, pValue);
	}

}
