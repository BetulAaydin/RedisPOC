package redislettuceclient.mapper;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;


public class ObjectMapperConvert {
	public static final String SESSION_KEY = "MAYANEXT_SESSION_KEY";
	private static final String SHARED_BARCODE = "barcode";
	private static final String SHARED_APPLICATION_ID = "applicationId";
	private static final String SHARED_CHANNEL_ID = "channelId";
	private static final String SHARED_PROCESS_ID = "processedId";
	
	public static String convertHashToString(Map<String, Object> mapPrintDocument) {
		ObjectMapper mapper = new ObjectMapper();
		
		SimpleModule module = new SimpleModule();
		Class<Map<String, Object>> mapClass = (Class<Map<String, Object>>) (Class) Map.class;
		module.addSerializer(new DateSerializer(mapClass));
		// module.addDeserializer(mapClass,new DateDeserializer(mapClass));
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.registerModule(module);
		String json1 = null;
		try {
			json1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapPrintDocument);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json1.toString();
	}

	public static ConcurrentHashMap<String, Object> convertJsonToHash(Object json1) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		Class<Map<String, Object>> mapClass = (Class<Map<String, Object>>) (Class) Map.class;
		module.addSerializer(new DateSerializer(mapClass));
		module.addDeserializer(mapClass, new DateDeserializer(mapClass));
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.registerModule(module);
		ConcurrentHashMap<String, Object> mapFromJson = null;
		try {
			mapFromJson = mapper.readValue(json1.toString(), ConcurrentHashMap.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("ObjectMapper" + mapFromJson);
		return mapFromJson;
	}

	public static Map<String, Object> getNestedtHashMap() {
		Integer processId = new Integer(10101);
		Integer channelId = 5;
		Boolean booleanDeger = new Boolean(false);
		Map<String, Object> mapPrintDocument = new HashMap<String, Object>();
		mapPrintDocument.put(SHARED_BARCODE, "b");
		mapPrintDocument.put(SHARED_APPLICATION_ID, Integer.valueOf(999000000));
		mapPrintDocument.put(SHARED_CHANNEL_ID, 9223372036854775807L);
		mapPrintDocument.put(SHARED_PROCESS_ID, processId);

		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("Dogum Gunu", "Cuma");
		subMap.put("Anne Adi", "Elife");
		Date elevenApril2018Date = new Date(120, 4, 4);
		subMap.put("date", elevenApril2018Date);
		mapPrintDocument.put("SUB", subMap);
		return mapPrintDocument;
	}
}
