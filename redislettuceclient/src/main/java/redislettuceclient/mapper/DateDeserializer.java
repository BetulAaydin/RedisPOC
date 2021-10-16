package redislettuceclient.mapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;


public class DateDeserializer extends StdDeserializer<Map<String, Object>> {
	private TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
	};

	protected DateDeserializer(Class<?> vc) {
		super(vc);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		Map<String, Object> map = new ObjectMapper().readValue(p, typeRef);
		return traceHashMap(map);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> traceHashMap(Map<String, Object> value) {

		Map<String, Object> adaptedValue = new HashMap<String, Object>(value);
		for (Map.Entry<String, Object> e : value.entrySet()) {
			if (e.getValue() instanceof Long
					&& (e.getKey().contains("Date") || e.getKey().contains("date") || e.getKey().contains("DATE"))) {
				adaptedValue.remove(e.getKey());
				adaptedValue.put(e.getKey(), new Date((Long) e.getValue()));
			}else
			if (e.getValue() instanceof Long) {
				System.out.println("Deserialization"+e.getKey());
				adaptedValue.remove(e.getKey());
				adaptedValue.put(e.getKey(), (Long) e.getValue());
			}
			if (e.getValue() instanceof Map) {
				traceHashMap((Map<String, Object>) e.getValue());
			}
		}

		return adaptedValue;
	}

}
