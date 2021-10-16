package redislettuceclient;

import java.util.concurrent.ConcurrentHashMap;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import redislettuceclient.codec.SerializedObjectCodec;
import redislettuceclient.mapper.ObjectMapperConvert;

public class RedisSerilazedCodecOperation {
	private StatefulRedisConnection<String, Object> connection=null;
	
	public void storeStandAloneRedis() {
		RedisAsyncCommands<String, Object> syncCommands = connection.async();
		RedisFuture<Boolean> future = syncCommands.hset("REDIS_storeStandAloneRedis", "RedisSerilazedCodecOperation_1",
				ObjectMapperConvert.convertHashToString(ObjectMapperConvert.getNestedtHashMap()));
			future.whenComplete((v, throwable) -> {
			if (throwable != null) {
			}
		});	
	
	}

	public void getStandAloneRedis() {
		RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
		RedisCommands<String, Object> syncCommands = connection.sync();
		ConcurrentHashMap<String, Object> concurentHasMap = ObjectMapperConvert.convertJsonToHash(
				syncCommands.hget("REDIS_storeStandAloneRedis", "RedisSerilazedCodecOperation_1"));
		System.out.println(concurentHasMap);
		
	}
	
	public void openConnection() {
		if(connection==null) {
		  RedisClient redisClient = RedisClient.create("redis://127.0.0.1:6379");
		  connection = redisClient.connect(new SerializedObjectCodec());	
		}
		
	}
	public void closeConnection() {
		if(connection==null) {
		  connection.close();
		}
		
	}
	
}
