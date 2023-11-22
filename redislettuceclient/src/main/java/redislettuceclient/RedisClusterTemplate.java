package redislettuceclient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;

public class RedisClusterTemplate {
	private static RedisClusterTemplate instance = null;
	private  ClusterClientOptions clusterClientOptions=null;
	StatefulRedisClusterConnection<String, Object> connection =null;

	 
	private void RedisClusterTemplate() { 
			
	}

	public static RedisClusterTemplate getInstance() {
		synchronized (RedisClusterTemplate.class) {
			if (instance == null) {
				instance = new RedisClusterTemplate();
				instance.initialize();
			}
		}
		return instance;
	}
	
	public synchronized StatefulRedisClusterConnection<String, Object> getConncetcion() {
		if (connection == null || !connection.isOpen()) {
			RedisClusterClient clusterClient = RedisClusterClient.create(getClusterUrlList());
			clusterClient.setOptions(clusterClientOptions);
			try {
				//JacsonSerilazedCustomCodec<Map<String, Object>> jackson2JsonRedisSerializer = new JacsonSerilazedCustomCodec(Map.class);	
				connection = clusterClient.connect(new SerializedObjectCodec());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	private void initialize(){
		if(clusterClientOptions==null){
		ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
				.enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
						ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
				.adaptiveRefreshTriggersTimeout(Duration.ofSeconds(30)).build();
		clusterClientOptions = ClusterClientOptions.builder().topologyRefreshOptions(refreshOptions).build();
		}
	}
	private static List<RedisURI> getClusterUrlList() {
		 List<RedisURI> list=new ArrayList();
		 list.add(RedisURI.Builder.redis("10.220.15.57").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
		 list.add(RedisURI.Builder.redis("10.220.15.58").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
		 list.add(RedisURI.Builder.redis("10.220.38.184").withPort(6379).withPassword("redisClusterMayaxxx129".toCharArray()).build());
		return 	list;
	
	}
}
