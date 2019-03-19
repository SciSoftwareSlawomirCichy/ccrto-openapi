package org.ccrto.openapi.values.json;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import pro.ibpm.mercury.attrs.javax.ListValue;

@SuppressWarnings("rawtypes")
@Component
public class ListValueDeserializer extends JsonDeserializer<ListValue> {

	@SuppressWarnings("unchecked")
	@Override
	public ListValue deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		List value = p.getCodec().readValue(p, List.class);
		return new ListValue(value);
	}

}
