package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pro.ibpm.mercury.attrs.javax.EntryValue;

@Component
public class EntryValueSerializer extends JsonSerializer<EntryValue> {

	@Override
	public void serialize(EntryValue value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		ObjectNode jsonObject;
		jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(EntryValue.PROPERTY_KEY, value.getKey());
		jsonObject.put(EntryValue.PROPERTY_VALUE, value.getValue());
		gen.writeObject(jsonObject);
		
	}

}
