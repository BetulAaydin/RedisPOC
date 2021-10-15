# RedisLettuceApplication
# Simple spring boot project for connection Redis and execute its commands.


```
## Run The Project
```txt
    mvn spring-boot:run
	 ```
    or
	```txt
   Run com.redis.redislettuce.RedislettuceApplication as a Java Application.
   ```

## Definition
 -End point:http://localhost:8080
 -Redis Server:redis://127.0.0.1:6379
 


### set key value operation
The curl command below requests for redislettuce/storeKeyValue operations to store as;
KEY= fistRediskey
VALUE=FirstRedisValue.

```txt
 curl http://localhost:8080/redislettuce/storeKeyValue/firstRediskey/FirstRedisValue
```

### get key value operation

```txt
KEY= fistRediskey
 >curl http://localhost:8080/redislettuce/getKeyValue/firstRediskey  
```


