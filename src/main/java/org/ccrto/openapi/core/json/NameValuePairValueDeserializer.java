package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.ccrto.openapi.core.CcrtoPropertyNameValuePair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class NameValuePairValueDeserializer extends JsonDeserializer<CcrtoPropertyNameValuePair> {

	@Override
	public CcrtoPropertyNameValuePair deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		JsonNode node = p.getCodec().readTree(p);
		String pName = null;
		if (node.has(CcrtoPropertyNameValuePair.PROPERTY_NAME)) {
			JsonNode name = node.get(CcrtoPropertyNameValuePair.PROPERTY_NAME);
			if (name.isTextual()) {
				pName = name.textValue();
			} else {
				pName = name.asText();
			}
		}
		String pValue = null;
		if (node.has(CcrtoPropertyNameValuePair.PROPERTY_VALUE)) {
			JsonNode value = node.get(CcrtoPropertyNameValuePair.PROPERTY_VALUE);
			if (value.isTextual()) {
				pValue = value.textValue();
			} else {
				pValue = value.asText();
			}
		}
		return CcrtoPropertyNameValuePair.getInstance(pName, pValue);
	}

}
