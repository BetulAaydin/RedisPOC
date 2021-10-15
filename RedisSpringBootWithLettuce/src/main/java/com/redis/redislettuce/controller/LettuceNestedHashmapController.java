package com.redis.redislettuce.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/redislettuce")
public class LettuceNestedHashmapController {
	
	@Autowired
	@Qualifier("redisTemplateStandAlone")
	 private RedisTemplate<String, Map<String, Object>> redisTemplate;
	
	
	@Autowired
	@Qualifier("redisClusterConnectionFactory")
	 private RedisTemplate<String, Map<String, Object>> redisClusterTemplate;
	
	@RequestMapping(value = "/storeKeyValue/{hashKey}/{value}", method = RequestMethod.GET)
    public @ResponseBody String storeNestedhash(@PathVariable String hashKey,@PathVariable String value) {	   	
	  redisTemplate.opsForValue().append(hashKey, value);	
		return 	HttpStatus.OK.toString();
    }
	
	
	@GetMapping("/getKeyValue/{hashKey}")
    public Object getNestedhash(@PathVariable String hashKey) {	
		 return redisTemplate.opsForValue().get(hashKey);
		 		
    }
	
	
	
}
