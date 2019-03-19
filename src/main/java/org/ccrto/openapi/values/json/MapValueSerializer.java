package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.javax.MapValue;

@Component
public class MapValueSerializer extends JsonSerializer<MapValue> {

	@Override
	public void serialize(MapValue list, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeObject(list.getValue());
	}

}
