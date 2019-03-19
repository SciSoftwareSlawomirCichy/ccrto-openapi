package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.javax.DateInString;

@Component
public class DateInStringSerializer extends JsonSerializer<DateInString> {

	@Override
	public void serialize(DateInString value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

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
