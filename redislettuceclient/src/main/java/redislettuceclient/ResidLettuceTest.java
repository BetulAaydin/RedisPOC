package redislettuceclient;

public class ResidLettuceTest {

	public static void main(String[] args) {
		RedisOperations redisoperation = new RedisOperations();
		redisoperation.storeRedis("10001");
		redisoperation.getSessionFromredis("10001");
		
	}

}
