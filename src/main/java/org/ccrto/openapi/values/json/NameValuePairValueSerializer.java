package org.ccrto.openapi.values.json;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pro.ibpm.mercury.attrs.javax.NameValuePairValue;
import pro.ibpm.mercury.entities.beans.NameValuePairBean;

@Component
public class NameValuePairValueSerializer extends JsonSerializer<NameValuePairValue> {

	@Override
	public void serialize(NameValuePairValue value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		ObjectNode jsonObject;
		jsonObject = JsonNodeFactory.instance.objectNode();
		jsonObject.put(NameValuePairBean.PROPERTY_NAME, value.getName());
		jsonObject.put(NameValuePairBean.PROPERTY_VALUE, value.getValue());
		gen.writeObject(jsonObject);

	}

}
