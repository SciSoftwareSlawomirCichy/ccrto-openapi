package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.core.CcrtoPropertyType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class StringValueDeserializer extends JsonDeserializer<CcrtoPropertyString> {

	@Override
	public CcrtoPropertyString deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		JsonNode node = p.getCodec().readTree(p);
		CcrtoPropertyString stringValue = new CcrtoPropertyString();
		if (node.isTextual()) {
			stringValue.setPropertyValue(node.textValue());
		} else {
			stringValue.setPropertyValue(node.asText());
			if (node.isLong() || node.isBigInteger()) {
				stringValue.setType(CcrtoPropertyType.LONG.getName());
			} else if (node.isInt()) {
				stringValue.setType(CcrtoPropertyType.INTEGER.getName());
			} else if (node.isNumber()) {
				stringValue.setType(CcrtoPropertyType.NUMBER.getName());
			}
		}
		return stringValue;
	}

}
