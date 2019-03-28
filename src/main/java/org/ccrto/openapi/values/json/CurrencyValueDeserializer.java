package org.ccrto.openapi.values.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.context.DecodeMethod;
import org.ccrto.openapi.values.CcrtoPropertyCurrency;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * 
 * CurrencyValueDeserializer
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CurrencyValueDeserializer extends JsonDeserializer<CcrtoPropertyCurrency> {

	@Override
	public CcrtoPropertyCurrency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

		/* deserializacja jest zawsze dla przychodzącego żądania */
		final boolean forRequest = false;

		Context context = null;
		if (p.getCodec() instanceof JsonObjectMapper) {
			JsonObjectMapper codec = (JsonObjectMapper) p.getCodec();
			context = codec.getContext();
		}
		JsonNode node = p.getCodec().readTree(p);
		CcrtoPropertyCurrency currencyValue;
		if (node.isTextual()) {
			String value = node.textValue();
			currencyValue = new CcrtoPropertyCurrency();
			currencyValue.setObjectValue(value);
			DecodeMethod decodeMethod = ContextHelper.getDecodeMethod(context, forRequest);
			boolean isNotEncoded = (DecodeMethod.ALL_WITHOUT_LOB.equals(decodeMethod)
					|| DecodeMethod.ALL.equals(decodeMethod));
			if (isNotEncoded) {
				currencyValue.setIsEncoded(isNotEncoded);
			}
		} else if (node.isNumber()) {
			Double valueAsDouble = (new BigDecimal(node.asText())).doubleValue();
			currencyValue = CcrtoPropertyCurrency.getInstance(context, valueAsDouble, forRequest);
		} else {
			currencyValue = new CcrtoPropertyCurrency();
		}
		return currencyValue;
	}

}
