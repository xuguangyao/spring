import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RedisTest {

    Logger logger = null;
    private Jedis jedis;
    @Before
    public void init(){
        jedis = new Jedis("10.126.88.219",6379);
        logger = LoggerFactory.getLogger(RedisTest.class);
    }

    /**
     * 操作String
     */
    //@Test
    public void StrTest(){
        //添加数据
        jedis.set("HP","惠普");
        //拼接数据
        jedis.append("HP","4300");
        //获取数据
        String value = jedis.get("HP");

        System.out.println(value);
        //删除某个键
        jedis.del("HP");
        value = jedis.get("HP");
        System.out.println(value);
        //设置多个键值对
        jedis.mset("name","zhangsan","age","23","qq","341234232");
        jedis.incr("age");
        jedis.incrBy("age",5);
        System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("qq"));
    }

    /**
     * 操作map
     */
    //@Test
    public void mapTest(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","lisi");
        map.put("age","25");
        map.put("qq","34234324");
        jedis.hmset("user:lisi",map);

        List<String> rsmap =  jedis.hmget("user:lisi","name","age","qq");
        System.out.println(rsmap);

        //删除map中某一个键值
        jedis.hdel("user:lisi","age");

        System.out.println(jedis.hmget("user:lisi","age"));
        System.out.println(jedis.hlen("user:lisi"));
        System.out.println(jedis.exists("user:lisi"));
        System.out.println(jedis.hkeys("user:lisi"));
        System.out.println(jedis.hvals("user:lisi"));

        Iterator<String> iter=jedis.hkeys("user:lisi").iterator();
        while (iter.hasNext()){
            String key = iter.next();
            System.out.println(key+":"+jedis.hmget("user:lisi",key));
        }

    }

    /**
     * 操作list
     */
    //@Test
    public void listTest(){
        //移除所有内容
        jedis.del("java framework");
        System.out.println(jedis.lrange("java framework",0,-1));

        //添加数据(左侧添加)
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","struts");
        jedis.lpush("java framework","hibernate");

        //获取数据
        System.out.println(jedis.lrange("java framework",0,-1));

        //删除数据
        jedis.del("java framework");
        //添加数据(右侧侧添加)
        jedis.rpush("java framework","spring");
        jedis.rpush("java framework","struts");
        jedis.rpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework",0,-1));


    }

    /**
     * 操作set
     */
    //@Test
    public void setTest(){
        //添加
        jedis.sadd("user","zhangsan");
        jedis.sadd("user","lisi");
        jedis.sadd("user","wangwu");
        jedis.sadd("user","ling");
        jedis.sadd("user","who");

        //移除
        jedis.srem("user","who");
        //获取所有加入的value
        System.out.println(jedis.smembers("user"));
        //判断who是否是user集合的元素
        System.out.println(jedis.sismember("user","who"));
        //随机返回
        System.out.println(jedis.srandmember("user"));
        //返回集合的个数
        System.out.println(jedis.scard("user"));

    }

    /**
     * 有序set
     */
    //@Test
    public void ZSetTest(){
        jedis.zadd("username",10.0,"zhangsan");
        jedis.zadd("username",5.0,"lisi");
        jedis.zadd("username",15.0,"wangwu");
        jedis.zadd("username",23.0,"ling");
        jedis.zadd("username",12.0,"who");

        //移除
        jedis.zrem("username","who");

        System.out.println(jedis.zrange("username",0,-1));
        jedis.zincrby("username",10,"wangwu");
        System.out.println(jedis.zscore("username","wangwu"));
        System.out.println(jedis.zrevrange("username",0,-1));

    }

    @Test
    public void  TestRedisPool(){
        RedisUtil.getJedis().set("newname","test");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }
}
