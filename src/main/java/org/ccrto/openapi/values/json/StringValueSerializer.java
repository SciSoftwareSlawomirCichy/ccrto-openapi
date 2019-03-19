package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.StringValue;

@Component
public class StringValueSerializer extends JsonSerializer<StringValue> {

	@Override
	public void serialize(StringValue value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		if (StringUtils.isBlank(value.getValue())) {
			gen.writeString((String) null);
		}
		AttributeType attrType = AttributeType.getType(value.getType());
		if (attrType.isNumber()) {
			gen.writeNumber(value.getValue());
		} else {
			gen.writeString(value.getValue());
		}

	}

}
