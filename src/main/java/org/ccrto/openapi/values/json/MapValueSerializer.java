package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.ccrto.openapi.values.CcrtoPropertyMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MapValueSerializer extends JsonSerializer<CcrtoPropertyMap> {

	@Override
	public void serialize(CcrtoPropertyMap list, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeObject(list.getValue());
	}

}
