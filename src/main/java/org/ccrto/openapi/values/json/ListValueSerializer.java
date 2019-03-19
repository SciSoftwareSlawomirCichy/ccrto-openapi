package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.javax.ListValue;

@SuppressWarnings("rawtypes")
@Component
public class ListValueSerializer extends JsonSerializer<ListValue> {

	@Override
	public void serialize(ListValue list, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeObject(list.getValue());
	}

}
