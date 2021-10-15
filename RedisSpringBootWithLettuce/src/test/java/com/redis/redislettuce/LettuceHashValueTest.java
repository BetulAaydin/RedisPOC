package com.redis.redislettuce;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import static org.mockito.Mockito.when;
@SpringBootTest
public class LettuceHashValueTest {
	   private final String SESSION_KEY="SESSION_KEY";
		private static final String SHARED_BARCODE = "barcode";
		private static final String SHARED_APPLICATION_ID = "applicationId";
		private static final String SHARED_CHANNEL_ID = "channelId";
		private static final String SHARED_PROCESS_ID="processedId";
	    @Mock
	    private RedisTemplate<String, Object> redisTemplate;
	    
	    @Mock
	    private HashOperations<String, Object, Object>  valueOperations;
	
	    @Test
	     void putHashValue() {
	    	Integer processId=new Integer(10101);
	    	Integer channelId=5;
	    	Boolean booleanDeger=new Boolean(false);
	    	Map<String, Object> mapPrintDocument = new HashMap<String, Object>();
			mapPrintDocument.put(SHARED_BARCODE, "barcode1*1**");
			mapPrintDocument.put(SHARED_APPLICATION_ID, 123);
			mapPrintDocument.put(SHARED_CHANNEL_ID, 5);
			mapPrintDocument.put(SHARED_PROCESS_ID, processId); 
			when(redisTemplate.opsForHash()).thenReturn(valueOperations);		     
			redisTemplate.opsForHash().put(SESSION_KEY, "session122324", mapPrintDocument);
	    	
	    }

}
