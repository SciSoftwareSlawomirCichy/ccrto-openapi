package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import pro.ibpm.mercury.attrs.CurrencyValueUtils;
import pro.ibpm.mercury.attrs.javax.CurrencyValue;

@Component
public class CurrencyValueSerializer extends JsonSerializer<CurrencyValue> {

	@Override
	public void serialize(CurrencyValue value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		String text = CurrencyValueUtils.toStringValue(/* context */ null, value);
		gen.writeString(text);
	}

}
