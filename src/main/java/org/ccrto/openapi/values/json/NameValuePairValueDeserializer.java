package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.javax.JSONNameValuePair;
import pro.ibpm.mercury.attrs.javax.NameValuePairValue;

@Component
public class NameValuePairValueDeserializer extends JsonDeserializer<NameValuePairValue> {

	@Override
	public NameValuePairValue deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode node = p.getCodec().readTree(p);
		String pKey = null;
		if (node.has(JSONNameValuePair.PROPERTY_NAME)) {
			JsonNode name = node.get(JSONNameValuePair.PROPERTY_NAME);
			if (name.isTextual()) {
				pKey = name.textValue();
			} else {
				pKey = name.asText();
			}
		}
		String pValue = null;
		if (node.has(JSONNameValuePair.PROPERTY_VALUE)) {
			JsonNode value = node.get(JSONNameValuePair.PROPERTY_VALUE);
			if (value.isTextual()) {
				pValue = value.textValue();
			} else {
				pValue = value.asText();
			}
		}
		return new NameValuePairValue(pKey, pValue);
	}

}
