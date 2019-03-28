package org.ccrto.openapi.values.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.ccrto.openapi.values.CcrtoPropertyDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class DateValueDeserializer extends JsonDeserializer<CcrtoPropertyDate> {

	@Override
	public CcrtoPropertyDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		JsonNode node = p.getCodec().readTree(p);
		CcrtoPropertyDate dateProperty = new CcrtoPropertyDate();
		if (node.isTextual()) {
			String value = node.textValue();
			dateProperty.setObjectValue(value);
		} else if (node.isNumber()) {
			Long value = (new BigDecimal(node.asText())).longValue();
			dateProperty.setIsEncoded(true);
			dateProperty.setObjectValue(Long.toString(value));
		}
		return dateProperty;
	}

}
