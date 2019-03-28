package org.ccrto.openapi.values.json;

import java.io.IOException;
import java.util.List;

import org.ccrto.openapi.values.CcrtoPropertyList;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@SuppressWarnings("rawtypes")
public class ListValueDeserializer extends JsonDeserializer<CcrtoPropertyList> {

	@SuppressWarnings("unchecked")
	@Override
	public CcrtoPropertyList deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		List value = p.getCodec().readValue(p, List.class);
		return new CcrtoPropertyList(value);
	}

}
