package redislettuceclient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import redislettuceclient.mapper.ObjectMapperConvert;

public class RedisJacsonSerializeOperations {
	public static final String SESSION_KEY = "SESSION_KEY";
	private static final String SHARED_BARCODE = "barcode";
	private static final String SHARED_APPLICATION_ID = "applicationId";
	private static final String SHARED_CHANNEL_ID = "channelId";
	private static final String SHARED_PROCESS_ID = "processedId";
	private StatefulRedisConnection<String, Object> connection = null;
	private StatefulRedisClusterConnection<String, Object> connectionCluster = null;

	public void storeClusterRedis() {
		RedisAdvancedClusterCommands<String, Object> syncCommands = connectionCluster.sync();
		syncCommands.hset("REDIS_LETTUCE_CLIENT", "sessionId_10989374",
				Jackson2HashMapperConvert.convertHashToString(getNestedtHashMap()));
	}

	public void getClusterRedis() {
		RedisAdvancedClusterCommands<String, Object> syncCommands = connectionCluster.sync();
		Map<String, Object> resultMap = Jackson2HashMapperConvert
				.convertJsonToHash(syncCommands.hget("REDIS_LETTUCE_CLIENT", "sessionId_10989374"));
		Map<String, Object> subMap = (Map<String, Object>) resultMap.get("SUB");
		System.out.println(subMap);

	}

	public void storeStandAloneRedis() {		
		RedisCommands<String, Object> syncCommands = connection.sync();
		syncCommands.hset("REDIS_STORE", "99999888888",
				Jackson2HashMapperConvert.convertHashToString(getNestedtHashMap()));
	
	}

	public void getStandAloneRedis() {
		RedisCommands<String, Object> syncCommands = connection.sync();
		Map<String, Object> hasMap = Jackson2HashMapperConvert
				.convertJsonToHash(syncCommands.hget("REDIS_STORE", "99999888888"));		
	}

	// Hastmap test data
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
		mapPrintDocument.put("SUB", subMap);
		return mapPrintDocument;
	}

	public void openClusterConnection() {
		if (connectionCluster == null) {
			List<RedisURI> list = new ArrayList();
			list.add(RedisURI.Builder.redis("masterNodeIp1").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
			list.add(RedisURI.Builder.redis("masterNodeIp2").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
			list.add(RedisURI.Builder.redis("masterNodeIp3").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
			Duration oneHours = Duration.ofHours(1);
			final ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
					.refreshPeriod(oneHours) // this one breaks it
					.enableAllAdaptiveRefreshTriggers().build();
			final ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder().autoReconnect(true)
					.maxRedirects(4).topologyRefreshOptions(refreshOptions).build();
			RedisClusterClient clusterClient = RedisClusterClient.create(list);
			clusterClient.setOptions(clusterClientOptions);
			JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(
					Map.class);
			connectionCluster = clusterClient.connect(jackson2JsonRedisSerializer);
		}
	}

	public void closeClusterConnection() {
		if (connectionCluster == null) {
			connectionCluster.close();
		}
	}

	public void openConnection() {
		if (connection == null) {
			RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
			JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(Map.class);
			connection = redisClient.connect(jackson2JsonRedisSerializer);
		}

	}

	public void closeConnection() {
		if (connection == null) {
			connection.close();
		}

	}
}
