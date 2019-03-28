package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.values.CcrtoPropertyDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateValueSerializer extends JsonSerializer<CcrtoPropertyDate> {

	@Override
	public void serialize(CcrtoPropertyDate value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		if (StringUtils.isBlank(value.getObjectValue())) {
			gen.writeString((String) null);
		}
		if (value.getIsEncoded() != null && value.getIsEncoded()) {
			gen.writeNumber(Long.parseLong(value.getObjectValue()));
		} else {
			gen.writeString(value.getObjectValue());
		}
	}

}
