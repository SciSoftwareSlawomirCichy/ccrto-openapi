package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.ccrto.openapi.values.CcrtoPropertyList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("rawtypes")
public class ListValueSerializer extends JsonSerializer<CcrtoPropertyList> {

	@Override
	public void serialize(CcrtoPropertyList list, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeObject(list.getValue());
	}

}
