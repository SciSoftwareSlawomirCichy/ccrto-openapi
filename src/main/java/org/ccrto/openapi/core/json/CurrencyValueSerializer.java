package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.ccrto.openapi.core.CcrtoPropertyCurrency;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CurrencyValueSerializer extends JsonSerializer<CcrtoPropertyCurrency> {

	@Override
	public void serialize(CcrtoPropertyCurrency value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		gen.writeString(value.getPropertyValue());
	}

}
