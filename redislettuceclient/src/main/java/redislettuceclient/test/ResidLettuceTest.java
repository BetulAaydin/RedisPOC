package redislettuceclient.test;

import redislettuceclient.RedisJacsonSerializeOperations;
import redislettuceclient.RedisSerilazedCodecOperation;

public class ResidLettuceTest {

	public static void main(String[] args) {
		
		RedisJacsonSerializeOperations redisoperation = new RedisJacsonSerializeOperations();
		/*
		redisoperation.openClusterConnection();
		redisoperation.storeClusterRedis();
		redisoperation.getClusterRedis();
		redisoperation.closeClusterConnection();
		*/
		
		redisoperation.openConnection();
		redisoperation.storeStandAloneRedis();
		redisoperation.getStandAloneRedis();
		redisoperation.closeConnection();
		
		
		
		RedisSerilazedCodecOperation redisSerilazedCodecOperation = new RedisSerilazedCodecOperation();
		redisSerilazedCodecOperation.openConnection();
		redisSerilazedCodecOperation.storeStandAloneRedis();
		redisSerilazedCodecOperation.getStandAloneRedis();
		redisSerilazedCodecOperation.closeConnection();
		
		
	}

}
