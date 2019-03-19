package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.javax.DateInMillis;

@Component
public class DateInMillisSerializer extends JsonSerializer<DateInMillis> {

	@Override
	public void serialize(DateInMillis value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		if (value.getMillis() == null) {
			gen.writeNumber((String) null);
		}
		gen.writeNumber(value.getMillis());
	}

}
