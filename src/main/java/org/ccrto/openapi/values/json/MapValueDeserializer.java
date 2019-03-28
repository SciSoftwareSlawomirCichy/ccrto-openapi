package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.ccrto.openapi.values.CcrtoPropertyEntry;
import org.ccrto.openapi.values.CcrtoPropertyMap;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class MapValueDeserializer extends JsonDeserializer<CcrtoPropertyMap> {

	@Override
	public CcrtoPropertyMap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		ArrayNode node = p.getCodec().readTree(p);
		CcrtoPropertyMap map = new CcrtoPropertyMap(node.size());
		for (int i = 0; i < node.size(); i++) {
			JsonNode entry = node.get(i);
			CcrtoPropertyEntry entryValue = getEntry(entry);
			map.put(entryValue.getKey(), entryValue.getValue());
		}
		return map;
	}

	private CcrtoPropertyEntry getEntry(JsonNode node) {
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
