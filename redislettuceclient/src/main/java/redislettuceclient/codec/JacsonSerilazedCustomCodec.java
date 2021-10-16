package redislettuceclient.codec;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.SerializationException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import io.lettuce.core.codec.RedisCodec;

public class JacsonSerilazedCustomCodec<T> implements RedisCodec<String, Object> {
	private ObjectMapper objectMapper = new ObjectMapper();
	static final byte[] EMPTY_ARRAY = new byte[0];
	private Charset charset = Charset.forName("UTF-8");	
	private final JavaType javaType;
	
	public JacsonSerilazedCustomCodec(Class<T> type) {
		super();
		this.javaType = getJavaType(type);		
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    	om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    	setObjectMapper(om);
	}

	public String decodeKey(ByteBuffer bytes) {
		 return charset.decode(bytes).toString();
	}

	

	public ByteBuffer encodeKey(String key) {
		 return charset.encode(key);
		
	}

	public ByteBuffer encodeValue(Object value) {
		if (value == null) {
			return ByteBuffer.wrap(EMPTY_ARRAY);
		}
		try {
			  return ByteBuffer.wrap(this.objectMapper.writeValueAsBytes(value));
			
		} catch (Exception ex) {
			throw new SerializationException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}
	

	public Object decodeValue(ByteBuffer bytes) {
		if (bytes==null) {
			return null;
		}
		try {		
			    byte[] array = new byte[bytes.remaining()];
	            bytes.get(array);
	       
			return (T) this.objectMapper.readValue(array, 0, array.length, javaType);
		} catch (Exception ex) {
			throw new SerializationException("Could not read JSON: " + ex.getMessage(), ex);
		}		
	}
	
	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.defaultInstance().constructType(clazz);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
}
