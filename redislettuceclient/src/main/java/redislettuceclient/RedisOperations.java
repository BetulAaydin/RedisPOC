package redislettuceclient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import redislettuceclient.codec.JacsonSerilazedCustomCodec;
import redislettuceclient.codec.SerializedObjectCodec;
import redislettuceclient.mapper.Jackson2HashMapperConvert;



public class RedisOperations {
	protected static final String SESSION_KEY = "NEW_APP:";
	private static final String SHARED_BARCODE = "barcode";
	private static final String SHARED_APPLICATION_ID = "applicationId";
	private static final String SHARED_CHANNEL_ID = "channelId";
	private static final String SHARED_PROCESS_ID = "processedId";

	public void storeRedis(String key) {	
		StatefulRedisClusterConnection<String, Object> connection = RedisClusterTemplate.getInstance().getConncetcion();
		try {
			RedisAdvancedClusterCommands<String, Object> syncCommands = connection.sync();
			System.out.println("Expire time"+syncCommands.ttl(SESSION_KEY+key));
			if(syncCommands.ttl(SESSION_KEY+key)<0){
				syncCommands.setex(SESSION_KEY+key,10000,getNestedtHashMap());
			} else {
				syncCommands.setex(SESSION_KEY+key,syncCommands.ttl(SESSION_KEY+key),getNestedtHashMap());
			}			
			syncCommands.set(SESSION_KEY+key,getNestedtHashMap());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void expire(String key) {	
		StatefulRedisClusterConnection<String, Object> connection = RedisClusterTemplate.getInstance().getConncetcion();
		try {
			RedisAdvancedClusterCommands<String, Object> syncCommands = connection.sync();
			syncCommands.expire(SESSION_KEY+key, 6000);
			} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public void getSessionFromredis(String key) {			 
		StatefulRedisClusterConnection<String, Object> connection = RedisClusterTemplate.getInstance().getConncetcion();
		RedisAdvancedClusterCommands<String, Object> syncCommands = connection.sync();	
	    syncCommands.get(SESSION_KEY+key);
	 	System.out.println(syncCommands.get(SESSION_KEY+key));
	
	}
	public void remove(String key) {
		List<RedisURI> list=new ArrayList();
		list.add(RedisURI.Builder.redis("node1").withPort(6379).withPassword("1234".toCharArray()).build());
		list.add(RedisURI.Builder.redis("node2").withPort(6379).withPassword("1234".toCharArray()).build());
		list.add(RedisURI.Builder.redis("node3").withPort(6379).withPassword("1234".toCharArray()).build());		
		Duration oneHours = Duration.ofHours(1);		
		 ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
	                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
	                        ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
	                .adaptiveRefreshTriggersTimeout(oneHours)
	                .build();
			final ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
			   // .autoReconnect(true)
			    //.maxRedirects(4)
			    .topologyRefreshOptions(refreshOptions)
			    .build();
		RedisClusterClient clusterClient = RedisClusterClient.create(list);
		clusterClient.setOptions(clusterClientOptions);
		//JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(Map.class);		 
		StatefulRedisClusterConnection<String, Object> connection = clusterClient.connect(new SerializedObjectCodec());	
		RedisAdvancedClusterCommands<String, Object> syncCommands = connection.sync();
		syncCommands.del(SESSION_KEY+key);
		connection.close();		
	}
	
	public void storeStandAloneRedis() {		
		 RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
		 JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(Map.class);	
		 StatefulRedisConnection<String, Object> connection = redisClient.connect(jackson2JsonRedisSerializer);	   
	     RedisCommands<String, Object>  syncCommands= connection.sync();	    
	     syncCommands.hset("REDIS_STORE", "99999888888", Jackson2HashMapperConvert.convertHashToString(getNestedtHashMap()));
	     redisClient.shutdown();
	     connection.close();
	   		
	}
	public void getStandAloneRedis(String key) {	
		
		 RedisClient redisClient = RedisClient.create(RedisURI.Builder.redis("10.220.15.57").withPort(7379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
		 JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(Map.class);		 
	     StatefulRedisConnection<String, Object> connection = redisClient.connect(jackson2JsonRedisSerializer);	   
	     RedisCommands<String, Object>  syncCommands= connection.sync();
	     Object o=syncCommands.get("MAYANEXT_SESSION:"+key);	    
	     Map<String, Object> hasMap= Jackson2HashMapperConvert.convertJsonToHash(o);
	     System.out.println(hasMap.entrySet());
	       //redisClient.shutdown();
		//connection.close();
	}
	
	
	public Map<String, Object> getNestedtHashMap() {
		Integer processId = new Integer(10101);
		Integer channelId = 5;
		Boolean booleanDeger = new Boolean(false);

		Map<String, Object> mapPrintDocument = new HashMap<String, Object>();
		mapPrintDocument.put(SHARED_BARCODE, "b");
		mapPrintDocument.put(SHARED_APPLICATION_ID, Integer.valueOf(110000));
		mapPrintDocument.put(SHARED_CHANNEL_ID, Long.valueOf(101));
		mapPrintDocument.put(SHARED_PROCESS_ID, processId);

		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subMap_1", "sub Map");
		subMap.put("subMap_2", 123);
		subMap.put("Date", new Date());
		Employee employee = new Employee();
		employee.setName("Worker_Name");
		employee.setSurname("Worker_Surname");
	    mapPrintDocument.put("SUB", subMap);
		Department dept = new Department();
		dept.setDeptName("BetulBirimi");
		dept.setId("freelance");
		employee.setDept(dept);
		Vector<String> vector = new Vector<String>();
		vector.add("Null Olma");
		employee.setProducts(vector);
		mapPrintDocument.put("employee", employee);
	    subMap.put("employee", employee);
		return mapPrintDocument;
	}


	public Map<String, Object> getNestedtHashMap(String key) {
		Integer processId = new Integer(10101);
		Integer channelId = 5;
		Boolean booleanDeger = new Boolean(false);
		
		Map<String, Object> mapPrintDocument = new HashMap<String, Object>();
		mapPrintDocument.put(SHARED_BARCODE, "b");
		mapPrintDocument.put(SHARED_APPLICATION_ID, Integer.valueOf(110000));
		mapPrintDocument.put(SHARED_CHANNEL_ID, Long.valueOf(101));
		mapPrintDocument.put(SHARED_PROCESS_ID, processId);

		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("subMap_1", "sub Map");
		subMap.put("subMap_2", 123);
		subMap.put("Date", new Date());				
		Employee employee=new Employee();
		employee.setName("Worker_Name");
		employee.setSurname("Worker_Surname");		
		mapPrintDocument.put("SUB", subMap);
		Department dept=new Department();
		dept.setDeptName("BetulBirimi"+key);
		dept.setId("freelance");
		employee.setDept(dept);
		subMap.put("employee", employee);
		return mapPrintDocument;
	}
}
