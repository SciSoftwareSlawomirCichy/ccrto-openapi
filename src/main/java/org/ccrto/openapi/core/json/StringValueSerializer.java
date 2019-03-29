package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.core.CcrtoPropertyType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class StringValueSerializer extends JsonSerializer<CcrtoPropertyString> {

	@Override
	public void serialize(CcrtoPropertyString value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {

		if (StringUtils.isBlank(value.getPropertyValue())) {
			gen.writeString((String) null);
		}
		CcrtoPropertyType fieldType = CcrtoPropertyType.getType(value.getType());
		if (fieldType.isNumber()) {
			gen.writeNumber(value.getPropertyValue());
		} else {
			gen.writeString(value.getPropertyValue());
		}

	}

}
