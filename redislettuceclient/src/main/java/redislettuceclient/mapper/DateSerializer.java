package redislettuceclient.mapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


public class DateSerializer extends StdSerializer<Map<String, Object>> {

	public DateSerializer(Class<Map<String, Object>> t) {
		super(t);
	}

	@Override
	public void serialize(Map<String, Object> value, JsonGenerator gen, SerializerProvider provider)
			throws IOException {
		Map<String, Object> adaptedValue=traceHashMap(new HashMap<String, Object>(value));
		System.out.println("Serialized Calisiyor"+adaptedValue);
		new ObjectMapper().writeValue(gen, adaptedValue);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> traceHashMap(Map<String, Object> value) {
		Map<String, Object> adaptedValue = new HashMap<String, Object>(value);
		for (Map.Entry<String, Object> e : value.entrySet()) {
			if (e.getValue() instanceof Date) {
				System.out.println(new Date().getTime()+""+e.getValue());
				e.setValue((Date)e.getValue());
				adaptedValue.put(e.getKey(),(Date)e.getValue());				
			}
			if(e.getValue() instanceof Long ) {
				System.out.println("*Long value*"+e.getKey());
				adaptedValue.put(e.getKey(),new Long(e.getValue().toString()));	
			}
			
			if (e.getValue() instanceof Map) {
				traceHashMap((Map<String, Object>) e.getValue());
			}
		}
		
		return adaptedValue;
	}

}
