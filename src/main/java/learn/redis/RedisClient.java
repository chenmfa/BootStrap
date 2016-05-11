package learn.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import learn.BaseLearn;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;

public class RedisClient extends BaseLearn{
	
	Logger log = LoggerFactory.getLogger(RedisClient.class);

	private Jedis jedis;//非切片客户端连接
	
	private JedisPool jedisPool;//非切片连接池
	
	private ShardedJedis shardJedis;//切片客户端连接
	
	private ShardedJedisPool shardedJedisPool;//切片客户端连接池
	
	public RedisClient(){
		initialRedisPool();
		initialShardedPool();
	}
	
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
	
	
	private void initialRedisPool(){
		
		JedisPoolConfig config = generateConfig();
		jedisPool = new JedisPool(config, "114.55.29.210",6379);
		log.info("Initial pool success");
	}
	
	private void initialShardedPool(){
		
		JedisPoolConfig config = generateConfig();
		
		List<JedisShardInfo> info = new ArrayList<JedisShardInfo>();
		info.add(new JedisShardInfo("114.55.29.210", 6379, "master"));
		
		shardedJedisPool = new ShardedJedisPool(config, info);
		log.info("Initial shard pool success.");
	}	
	
	public void testJedisFunc(){

		//单片操作
		Set<String> keys = jedis.keys("*");
		Map<String, String> key_val = jedis.hgetAll("test");
		log.info(key_val.toString());
		
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String key = it.next();
			//log.info("当前系统中有键: "+ key);
			if(key.length()>15){
				log.info(jedis.get(key));
			}
			String typeKey = jedis.type(key);//其他方法：类似move("key",index), rename("oldkey", "newkey")
			
			//log.info(typeKey);
		}
		
		//多片操作
		Boolean exists = shardJedis.exists("test");
		log.info("是否存在test的对象: "+(exists ?"是":"否"));
		shardJedis.set("cmf", "cheermanfa");
		//jedis.del("cmf");  //删除键信息
		//shardJedis.del("cmf");
		Boolean existsCMF = shardJedis.exists("cmf");
		log.info("是否存在cmf的对象: "+(existsCMF ?"是":"否"));
		//设置key的生效时长
		shardJedis.expire("cmf", 6);
		
		try {
	    Thread.sleep(2000);
    } catch (InterruptedException e) {
	    log.error("等待出错", e);	    
    }
		
		Long ttl = shardJedis.ttl("cmf");
		log.info("当前键cmf的剩余生存时间有:"+ ttl);
		//移除cmf的生存时间
		Long persist = shardJedis.persist("cmf");
		log.info("persist is：" +persist);
		//再次查看cmf的剩余生存时间
		Long ttl2 = shardJedis.ttl("cmf");
		log.info("cmf的剩余生存时间:"+ ttl2);//清除失效时间之后，剩余时间无限大
		
		//查看键所属的类型		
	}
	
	
	//测试字符串键值对
	public void testStringType(){
		
		jedis.set("key001", "value0001");
		jedis.set("key002", "value0002");
		jedis.set("key003", "value0003");
		jedis.set("key004", "value0004");
		
		//获取单个 //jedis.get("key001");
		//获取多个
		List<String> values = jedis.mget("key001","key002","key003","key004");
		log.info(values.toString());
		jedis.del("key001");
		log.info("删除后的值："+jedis.get("key001"));
		
		
		//在原本的后面附加		
		jedis.append("key002", "cmf");
		log.info(jedis.mget("key001","key002","key003","key004","key005").toString());
		
		//批量删除
		jedis.del("key001","key003","key005");
		log.info("批量删除后: "+jedis.mget("key001","key002","key003","key004","key005").toString());
		
		//批量新增
		jedis.mset("key001","newvalue_0001","key003","newkey0003","key005","newkey0005");
		log.info("批量新增后："+jedis.mget("key001","key002","key003","key004","key005").toString());
		
		//新增但是不覆盖,有一个重复就不设置了
		jedis.msetnx("key005","fugai","key006","fugai0006");
		//jedis.msetnx("key006","fugai0006");
		log.info("不覆盖新增后："+jedis.mget("key001","key002","key003","key004","key005","key006").toString());
		
		//获取并修改原值
		jedis.set("key004","value0004");
		log.info(jedis.getSet("key004", "key004-after-new"));
		log.info(jedis.get("key004"));
		
		log.info(jedis.getrange("key005",6, 9));
	}
	
	//测试集合缓存
	public void testListType(){
		jedis.lpush("stringlists", "Vector");
		jedis.lpush("stringlists", "LinkedList");
		jedis.lpush("stringlists", "MapList");
		jedis.lpush("stringlists", "Vector");
		jedis.lpush("stringlists", "Vector");
		jedis.lpush("stringlists", "SerialList");
		jedis.lpush("stringlists", "HashList");
		
		jedis.lpush("numberlists", "1","2","3","4","5");
		
		//查看所有元素，若为负数则从倒数的开始
		List<String> stringlists = jedis.lrange("stringlists", 0, -1);
		List<String> numberlists = jedis.lrange("numberlists", 0, -1);
		
		log.info(stringlists.toString());
		log.info(numberlists.toString());
		
		// 删除列表指定的值 ，第二个参数为删除的个数（有重复时），
		// 后add进去的值先被删，类似于出栈
		//lrem 返回最后修改的个数
		log.info(jedis.lrem("stringlists", 1, "Vector").toString());
		log.info(jedis.lrange("stringlists", 0, -1).toString());
		
		// 删除区间以外的数据 
		//-1为倒数第一个，所以就是不删除了
    log.info("删除下标0-3区间之外的元素："+jedis.ltrim("stringlists", 0, -1));
    log.info("删除了元素之后的集合内容："+jedis.lrange("stringlists", 0, -1));
    
    //元素出栈,后进先出
    String pop_str = jedis.lpop("stringlists");
    log.info("出栈的元素："+pop_str);
    log.info("出栈完之后的元素："+jedis.lrange("stringlists", 0, -1));
    
    //修改某个key下面的指定下标位置的对象
    jedis.lset("stringlists", 0, "changelist");        
    log.info(jedis.lrange("stringlists", 0, -1).toString());
    
    
    //结果排序
    //如果存储的值是string类型的，
    //会报错ERR One or more scores can't be converted into double
    //需要使用SortingParams
    SortingParams params =  new SortingParams();
    params.alpha();
    params.desc();
    List<String> sortList = jedis.sort("stringlists",params);
    List<String> sortNumber = jedis.sort("numberlists");
    log.info(sortList.toString());
    log.info(sortNumber.toString());
    
    //获取下标为0的元素
    log.info(jedis.lindex("stringlists", 0));
    
    jedis.flushDB();
	}
	
	public void testSetType(){
		
		jedis.sadd("set_list", "Set0001");
		jedis.sadd("set_list", "Set0002");
		jedis.sadd("set_list", "Set0005");
		jedis.sadd("set_list", "Set0004");
		
		jedis.sadd("set_list2","Set0002");
		jedis.sadd("set_list2","Set0003");
		jedis.sadd("set_list2","Set0005");
		jedis.sadd("set_list2","Set0006");
				
		//显示set里面的所有集合
		log.info(jedis.smembers("set_list").toString());
		
		//查看set集合是否包含某元素
		log.info("是否包含Set0001元素："+(jedis.sismember("set_list", "Set0001")?"是":"否"));
		log.info("是否包含Set0003元素："+(jedis.sismember("set_list", "Set0003")?"是":"否"));
		
		
		//
		
		//删除元素
		jedis.srem("set_list", "Set0002");
		log.info("删除元素后结果为："+jedis.smembers("set_list").toString());
		
		
		
		jedis.del("set_list","set_list2");
		log.info("清空set_list后："+jedis.smembers("set_list").toString());
		
		jedis.flushDB();		
	}
	
	public void getResource(){
		try {
	    jedis = jedisPool.getResource(); //获取连接池对象(无分片的)
	    
	    shardJedis = shardedJedisPool.getResource();//获取共享连接池对象(有分片)
    } catch (Exception e) {
	    log.error("获取连接异常,重新获取",e);
	    getResource();
    }
	}
	
	public void returnResource(){
		if(null !=jedis){
			jedis.close();
		}
			
		if(null != shardJedis){
			shardJedis.close();
		}	
	}
	
	public static void main(String[] args) {
		RedisClient rds = new RedisClient();
		rds.getResource();
				
		//rds.testSetType();
		//rds.testListType();
		//rds.testStringType();
		rds.testJedisFunc();
		rds.returnResource();
  }
	
	
}
