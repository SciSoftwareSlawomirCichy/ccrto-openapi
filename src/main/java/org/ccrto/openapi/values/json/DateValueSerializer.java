package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.values.DateValue;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateValueSerializer extends JsonSerializer<DateValue> {

	@Override
	public void serialize(DateValue value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (StringUtils.isBlank(value.getString())) {
			gen.writeString((String) null);
		}
		if (value.isEncoded()) {
			gen.writeNumber(Long.parseLong(value.getString()));
		} else {
			gen.writeString(value.getString());
		}
	}

}
