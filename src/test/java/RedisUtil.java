import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {
    //Redis服务器ip
    private static String IP = "10.126.88.219";
    //redis端口号
    private static int PORT = 6379;

    //访问密码
    private static String AUTH = "";

    //可用连接实例的最大数目，默认：8
    //如果赋值为-1.表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
    //控制一个pool里面最多有多少个状态的idle(空闲)的redis实例，默认值8
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值-1，表示永不超时，如果超过等待时间，直接抛弃
    private static int MAX_WAIT = 10000;

    private static int TIME_OUT = 10000;
    //在borrow一个jedis实例时，是否提前进行validate操作；如果true，则得到的redis实例均可用
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);

            //jedisPool = new JedisPool(config,IP,PORT,TIME_OUT,AUTH);
            jedisPool = new JedisPool(config,IP,PORT,TIME_OUT);
        }catch (Exception e){

        }
    }
    /**
     * 获取jedis实例
     */
    public synchronized static Jedis getJedis(){
        try {
            if (jedisPool != null){
                Jedis resource = jedisPool.getResource();
                return resource;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 释放jedis资源
     *
     */
    public static void releaseResource(final Jedis jedis){
        if(jedis != null){
            jedisPool.returnResource(jedis);
        }
    }
}



