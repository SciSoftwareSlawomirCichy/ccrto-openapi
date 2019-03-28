package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.ccrto.openapi.values.CcrtoPropertyEntry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class EntryValueDeserializer extends JsonDeserializer<CcrtoPropertyEntry> {

	@Override
	public CcrtoPropertyEntry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		JsonNode node = p.getCodec().readTree(p);
		String pKey = null;
		if (node.has(CcrtoPropertyEntry.PROPERTY_KEY)) {
			JsonNode key = node.get(CcrtoPropertyEntry.PROPERTY_KEY);
			if (key.isTextual()) {
				pKey = key.textValue();
			} else {
				pKey = key.asText();
			}
		}
		String pValue = null;
		if (node.has(CcrtoPropertyEntry.PROPERTY_VALUE)) {
			JsonNode value = node.get(CcrtoPropertyEntry.PROPERTY_VALUE);
			if (value.isTextual()) {
				pValue = value.textValue();
			} else {
				pValue = value.asText();
			}
		}
		return new CcrtoPropertyEntry(pKey, pValue);
	}

}
