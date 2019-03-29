package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.ccrto.openapi.core.CcrtoPropertyEntry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EntryValueSerializer extends JsonSerializer<CcrtoPropertyEntry> {

	@Override
	public void serialize(CcrtoPropertyEntry value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		ObjectNode jsonObject;
		jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(CcrtoPropertyEntry.PROPERTY_KEY, value.getKey());
		jsonObject.put(CcrtoPropertyEntry.PROPERTY_VALUE, value.getValue());
		gen.writeObject(jsonObject);

	}

}
