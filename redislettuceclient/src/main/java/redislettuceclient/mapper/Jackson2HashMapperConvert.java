package redislettuceclient.mapper;

import java.util.Map;

import org.springframework.data.redis.hash.Jackson2HashMapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Jackson2HashMapperConvert {
	public static  Object convertHashToString(Map<String, Object> mapPrintDocument) {
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		 mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		  Jackson2HashMapper  jaksonMapper=new Jackson2HashMapper(mapper,false);	
		 return jaksonMapper.fromHash(mapPrintDocument);
}
	
	public static Map<String, Object> convertJsonToHash(Object json1) {
		 ObjectMapper mapper = new ObjectMapper();
		 mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		 mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);		 
		 Jackson2HashMapper  jaksonMapper=new Jackson2HashMapper(mapper,false);
		 Map<String, Object> mapFromJson =null;           
     mapFromJson = jaksonMapper.toHash(json1);      
  return mapFromJson;
}
}
