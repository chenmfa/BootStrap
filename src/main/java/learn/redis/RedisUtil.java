package learn.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	
	private Jedis jedis;//非切片客户端连接
	
	private JedisPool jedisPool;//非切片连接池
	
	public RedisUtil(){
		initialRedisPool();
	}
	
	//初始化连接池
	private void initialRedisPool(){		
		JedisPoolConfig config = generateConfig();
		jedisPool = new JedisPool(config, "114.55.29.210",6379);
		System.out.println("Initial pool success");
	}
	
	//生成redis连接池配置
	private JedisPoolConfig generateConfig(){
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		//Idle时进行连接扫描
		config.setTestWhileIdle(true);
		//表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		//表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);
		return config;
	}
	
//获取redis连接
	public void getResource(){
		if(jedis == null){
			try {
		    jedis = jedisPool.getResource(); //获取连接池对象(无分片的)		    
	    } catch (Exception e) {
		    System.out.println("获取连接异常,重新获取");
		    getResource();
	    }
		}
	}
	
	//释放连接
	public void returnResource(){
		if(null !=jedis){
			jedis.close();
		}
	}
	
	//判断当前查询的键是否存在
	public boolean isExists(String hashCode){
		getResource();
		Boolean exists = jedis.exists(hashCode);
		return exists;
	}
	
	//获取已缓存的json字符
	public String getCacheObject(String hashCode){
		getResource();
		if(!jedis.exists(hashCode)){
			System.out.println("查询的信息结果已过期："+hashCode);
			return null;
		}
		String json = jedis.get(hashCode);		
		returnResource();
		return json;
	}
	
	//保存json 字符串
	public boolean storeJSON(String hashCode, String jsonObj){
		getResource();
		if(jedis.exists(hashCode)){
			System.out.println("当前查询结果的信息已存在，暂不覆盖："+hashCode);
			return false;
		}
		jedis.set(hashCode, jsonObj);
		returnResource();
		return true;
	}
	
}
