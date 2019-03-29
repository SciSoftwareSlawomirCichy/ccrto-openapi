package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.CcrtoPropertyDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateValueSerializer extends JsonSerializer<CcrtoPropertyDate> {

	@Override
	public void serialize(CcrtoPropertyDate value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		if (StringUtils.isBlank(value.getPropertyValue())) {
			gen.writeString((String) null);
		}
		if (value.getIsEncoded() != null && value.getIsEncoded()) {
			gen.writeNumber(Long.parseLong(value.getPropertyValue()));
		} else {
			gen.writeString(value.getPropertyValue());
		}
	}

}
