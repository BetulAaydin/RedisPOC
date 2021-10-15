package com.redis.redislettuce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.Map;


import org.springframework.data.redis.serializer.StringRedisSerializer;



@Configuration
public class RedisConfiguration {
	@Autowired
    private RedisProperties redisProperties;
 
    @Value("${redis.maxRedirects:3}")
    private int maxRedirects;
 
    @Value("${redis.refreshTime:5}")
    private int refreshTime;
 
    @Bean
    public LettuceConnectionFactory redisStandAloneConnectionFactory() {
    	 return new LettuceConnectionFactory(new RedisStandaloneConfiguration("127.0.0.1", 6379));
    }
 
 
 
    @Bean
    public RedisTemplate<String, Map<String, Object>> redisTemplateStandAlone(@Qualifier("redisStandAloneConnectionFactory")LettuceConnectionFactory redisConnectionFactory) {    	      
        RedisTemplate<String, Map<String, Object>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());         
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

   

}
