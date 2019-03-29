package org.ccrto.openapi.core.json;

import java.io.IOException;

import org.ccrto.openapi.core.CcrtoPropertyNameValuePair;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class NameValuePairValueSerializer extends JsonSerializer<CcrtoPropertyNameValuePair> {

	@Override
	public void serialize(CcrtoPropertyNameValuePair value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {

		ObjectNode jsonObject;
		jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(CcrtoPropertyNameValuePair.PROPERTY_NAME, value.getName());
		jsonObject.put(CcrtoPropertyNameValuePair.PROPERTY_VALUE, value.getValue());
		gen.writeObject(jsonObject);

	}

}
