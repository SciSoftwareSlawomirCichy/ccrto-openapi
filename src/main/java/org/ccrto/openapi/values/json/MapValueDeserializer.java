package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import pro.ibpm.mercury.attrs.javax.EntryValue;
import pro.ibpm.mercury.attrs.javax.MapValue;

@Component
public class MapValueDeserializer extends JsonDeserializer<MapValue> {

	@Override
	public MapValue deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		ArrayNode node = p.getCodec().readTree(p);
		MapValue map = new MapValue(node.size());
		for (int i = 0; i < node.size(); i++) {
			JsonNode entry = node.get(i);
			EntryValue entryValue = getEntry(entry);
			map.put(entryValue.getKey(), entryValue.getValue());
		}
		return map;
	}

	private EntryValue getEntry(JsonNode node) {
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
