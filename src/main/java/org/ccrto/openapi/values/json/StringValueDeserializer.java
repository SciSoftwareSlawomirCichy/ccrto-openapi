package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.StringValue;

@Component
public class StringValueDeserializer extends JsonDeserializer<StringValue> {

	@Override
	public StringValue deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		JsonNode node = p.getCodec().readTree(p);
		StringValue stringValue = new StringValue();
		if (node.isTextual()) {
			stringValue.setValue(node.textValue());
		} else {
			stringValue.setValue(node.asText());
			if (node.isNumber()) {
				stringValue.setType(AttributeType.NUMBER.getName());
			}
		}
		return stringValue;
	}

}
