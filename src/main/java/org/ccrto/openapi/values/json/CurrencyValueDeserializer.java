package org.ccrto.openapi.values.json;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import pro.ibpm.mercury.attrs.CurrencyValueUtils;
import pro.ibpm.mercury.attrs.javax.CurrencyValue;
import pro.ibpm.mercury.context.Context;
import pro.ibpm.mercury.json.JsonDeserializerException;
import pro.ibpm.mercury.json.JsonObjectMapper;

@Component
public class CurrencyValueDeserializer extends JsonDeserializer<CurrencyValue> {

	@Override
	public CurrencyValue deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		Context context = null;
		if (p.getCodec() instanceof JsonObjectMapper) {
			JsonObjectMapper codec = (JsonObjectMapper) p.getCodec();
			context = codec.getContext();
		}

		JsonNode node = p.getCodec().readTree(p);
		CurrencyValue currencyValue = new CurrencyValue();
		String value;
		if (node.isTextual()) {
			value = node.textValue();
		} else {
			value = node.asText();
		}
		try {
			CurrencyValueUtils.setFromStringValue(context, value, currencyValue);
		} catch (ParseException e) {
			Locale lLoc = CurrencyValueUtils.getLocale(context, currencyValue);
			String formatPattern = CurrencyValueUtils.getFormatPattern(context, currencyValue);
			String defaultCode = CurrencyValueUtils.getCurrencyCode(context, currencyValue);
			throw new JsonDeserializerException(
					String.format("[CurrencyValue] value: %s (locale='%s', formatPattern='%s', code='%s')", value, lLoc,
							formatPattern, defaultCode),
					e);
		}
		return currencyValue;
	}

}
