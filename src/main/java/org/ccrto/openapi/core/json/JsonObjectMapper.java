package org.ccrto.openapi.core.json;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ccrto.openapi.core.CcrtoPropertyCurrency;
import org.ccrto.openapi.core.CcrtoPropertyDate;
import org.ccrto.openapi.core.CcrtoPropertyEntry;
import org.ccrto.openapi.core.CcrtoPropertyList;
import org.ccrto.openapi.core.CcrtoPropertyMap;
import org.ccrto.openapi.core.CcrtoPropertyNameValuePair;
import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.core.Context;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 
 * JsonObjectMapper mapowanie obiektów JSON z dodatkowymi, charakterystycznymi
 * dla MRC serializatorami i deserializatorami.
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class JsonObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7256619723376026926L;
	public static final String CONTEXT_ATTRIBUTE = "context";
	public static final Pattern SIMPLE_JSON_IDENTYFICATON = Pattern
			.compile("[{\\[]{1}([,:{}\\[\\]0-9.\\-+Eaeflnr-u \\n\\r\\t]|\".*?\")+[}\\]]{1}");

	/**
	 * Lista deserializatorów obsługujących wartości reprezentowane przez atrybuty
	 * sprawy.
	 */
	@SuppressWarnings("rawtypes")
	private static Deserializer[] attrDeserializers = {
			new Deserializer<CcrtoPropertyCurrency>(CcrtoPropertyCurrency.class, new CurrencyValueDeserializer()),
			new Deserializer<CcrtoPropertyDate>(CcrtoPropertyDate.class, new DateValueDeserializer()),
			new Deserializer<CcrtoPropertyEntry>(CcrtoPropertyEntry.class, new EntryValueDeserializer()),
			new Deserializer<CcrtoPropertyList>(CcrtoPropertyList.class, new ListValueDeserializer()),
			new Deserializer<CcrtoPropertyMap>(CcrtoPropertyMap.class, new MapValueDeserializer()),
			new Deserializer<CcrtoPropertyNameValuePair>(CcrtoPropertyNameValuePair.class, new NameValuePairValueDeserializer()),
			new Deserializer<CcrtoPropertyString>(CcrtoPropertyString.class, new StringValueDeserializer()), };

	/**
	 * Lista serializatorów obsługujących wartości reprezentowane przez atrybuty
	 * sprawy.
	 */
	@SuppressWarnings("rawtypes")
	private static Serializer[] attrSerializers = {
			new Serializer<CcrtoPropertyCurrency>(CcrtoPropertyCurrency.class, new CurrencyValueSerializer()),
			new Serializer<CcrtoPropertyDate>(CcrtoPropertyDate.class, new DateValueSerializer()),
			new Serializer<CcrtoPropertyEntry>(CcrtoPropertyEntry.class, new EntryValueSerializer()),
			new Serializer<CcrtoPropertyList>(CcrtoPropertyList.class, new ListValueSerializer()),
			new Serializer<CcrtoPropertyMap>(CcrtoPropertyMap.class, new MapValueSerializer()),
			new Serializer<CcrtoPropertyNameValuePair>(CcrtoPropertyNameValuePair.class, new NameValuePairValueSerializer()),
			new Serializer<CcrtoPropertyString>(CcrtoPropertyString.class, new StringValueSerializer()), };

	/** Kontekst wykonywanego mapowania */
	private Context context;

	private JsonObjectMapper() {
	}

	/**
	 * Pobieranie obiektu mapper'a do deserializacji, czyli przekształcania JSON do
	 * obiektu Java
	 * 
	 * @see #attrDeserializers
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param deserializers
	 *            dodatkowe deserializatory pomocne w poprawnym przekształceniu
	 * @return instancja obiektu mapper'a z dodatkowymi i predefiniowanymi
	 *         deserializatorami.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ObjectMapper getDeserializer(Context context, Deserializer... deserializers) {
		SimpleModule module;

		JsonObjectMapper mapper = new JsonObjectMapper();
		mapper.setContext(context);

		module = new SimpleModule();
		/* ustawienie dodatkowych deserializatorów */
		if (deserializers != null && deserializers.length > 0) {
			for (int i = 0; i < deserializers.length; i++) {
				module.addDeserializer(deserializers[i].clazz, deserializers[i].deserializer);
			}
		}
		/* Predefiniowane deserializatory MRC - START */
		for (int i = 0; i < attrDeserializers.length; i++) {
			module.addDeserializer(attrDeserializers[i].clazz, attrDeserializers[i].deserializer);
		}
		/* Predefiniowane deserializatory MRC - KONIEC */
		mapper.registerModule(module);
		return mapper;
	}

	/**
	 * Pobieranie obiektu mapper'a do serializacji, czyli przekształcania obiektu
	 * Java do JSON
	 * 
	 * @see #attrSerializers
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param serializers
	 *            dodatkowe serializatory pomocne w poprawnym przekształceniu
	 * @return instancja obiektu mapper'a z dodatkowymi i predefiniowanymi
	 *         serializatorami.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ObjectMapper getSerializer(Context context, Serializer... serializers) {
		SimpleModule module;

		JsonObjectMapper mapper = new JsonObjectMapper();
		mapper.setContext(context);

		module = new SimpleModule();
		/* ustawienie dodatkowych serializatorów */
		if (serializers != null && serializers.length > 0) {
			for (int i = 0; i < serializers.length; i++) {
				module.addSerializer(serializers[i].clazz, serializers[i].serializer);
			}
		}
		/* Predefiniowane serializatory MRC - START */
		for (int i = 0; i < attrSerializers.length; i++) {
			module.addSerializer(attrSerializers[i].clazz, attrSerializers[i].serializer);
		}
		/* Predefiniowane serializatory MRC - KONIEC */
		mapper.registerModule(module);
		return mapper;
	}

	/**
	 * Metoda pomocnicza sprawdzająca czy dany ciąg znakowy jest reprezentacją
	 * JSON'a i jeżeli tak utworzy instancję obiektu {@link JsonNode}.
	 * 
	 * @param mapper
	 *            instancja mapper'a obiektów JSON
	 * @param value
	 *            analizowana wartość
	 * @return instancja {@link JsonNode}, lub {@code null} jeżeli okaże się, że
	 *         analizowana wartość nie jest reprezentacją JSON.
	 */
	public static JsonNode checkAndCreateJsonNode(ObjectMapper mapper, String value) {
		JsonNode node = null;
		if (value.startsWith("{") || value.startsWith("[")) {
			/* sprawdzam czy jest to JSON */
			Matcher m = SIMPLE_JSON_IDENTYFICATON.matcher(value);
			if (m.find()) {
				try {
					node = mapper.readTree(value);
				} catch (IOException e) {
					/* ignoruj niepowodzenia */
				}
			}
		}
		return node;
	}

	/**
	 * 
	 * MrcDeserializer pomocnicza klasa pozwalająca na przekazanie definicji
	 * deserializatora
	 *
	 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
	 * @version $Revision: 1.1 $
	 *
	 * @param <T>
	 *            dla spójności klasa deserializatora
	 */
	public static class Deserializer<T> {
		/** Klasa obiektów obsługiwanych przez deserializator */
		private final Class<T> clazz;
		/** Obiekt deserializatora */
		private final JsonDeserializer<T> deserializer;

		public Deserializer(Class<T> clazz, JsonDeserializer<T> deserializer) {
			super();
			this.clazz = clazz;
			this.deserializer = deserializer;
		}

		/**
		 * @return the {@link #clazz}
		 */
		public Class<T> getClazz() {
			return clazz;
		}

		/**
		 * @return the {@link #deserializer}
		 */
		public JsonDeserializer<T> getDeserializer() {
			return deserializer;
		}
	}

	/**
	 * 
	 * MrcSerializer pomocnicza klasa pozwalająca na przekazanie definicji
	 * serializatora
	 *
	 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
	 * @version $Revision: 1.1 $
	 *
	 * @param <T>
	 *            dla spójności klasa serializatora
	 */
	public static class Serializer<T> {
		/** Klasa obiektów obsługiwanych przez serializator */
		private final Class<T> clazz;
		/** Obiekt serializatora */
		private final JsonSerializer<T> serializer;

		public Serializer(Class<T> clazz, JsonSerializer<T> serializer) {
			super();
			this.clazz = clazz;
			this.serializer = serializer;
		}

		/**
		 * @return the {@link #clazz}
		 */
		public Class<T> getClazz() {
			return clazz;
		}

		/**
		 * @return the {@link #serializer}
		 */
		public JsonSerializer<T> getSerializer() {
			return serializer;
		}

	}

	/**
	 * @return the {@link #context}
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the {@link #context} to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}
}
