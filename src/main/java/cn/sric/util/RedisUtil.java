package cn.sric.util;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用前加入 redis 依赖
 * <!--Redis依赖-->
 * <dependency>
 * <groupId>org.springframework.boot</groupId>
 * <artifactId>spring-boot-starter-data-redis</artifactId>
 * </dependency>
 *
 * @author sric
 */
@Component(value = "redisUtil")
public class RedisUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean set(String key, String value) {
        boolean flag = false;
        try {
            stringRedisTemplate.opsForValue().set(key, value);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public boolean set(String key, String value, long timeout) {
        boolean flag = false;
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    public boolean set(String key, String value, long timeout, TimeUnit timeUnit) {
        boolean flag = false;
        try {
            stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public boolean setNX(String key, String value) {
        RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory().getConnection();
        return redisConnection.setNX(key.getBytes(), new byte[]{1});
    }


    /**
     * 设置过期时间是凌晨0点0分0秒
     * 如果存在不进行设置
     *
     * @param key
     * @param value
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean putTime(String key, String value) {
        //获取剩余时间
        long seconds = (RedisUtil.getMiol() - System.currentTimeMillis()) / 1000;
//        boolean b = setNX(key, value);

//        if (b) {
//            try {
//                if (expire(key, seconds, TimeUnit.SECONDS)) {
//                    return true;
//                }
//            } catch (Exception e) {
//                del(key);
//                e.printStackTrace();
//            }
//        }
        return set(key, value, seconds, TimeUnit.SECONDS);
    }


    public boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    public boolean expire(String key, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key, time, timeUnit);
    }

    public Set<String> keys(String keyPatterns) {
        return stringRedisTemplate.keys(keyPatterns);
    }


    public boolean exists(String key) {
        RedisConnection redisConnection = stringRedisTemplate.getConnectionFactory().getConnection();
        return redisConnection.exists(key.getBytes());
    }

    public void setDatabase(int index) {
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) stringRedisTemplate.getConnectionFactory();
        connectionFactory.setDatabase(index);
    }


    public static Long getMiol() {
        Calendar ca = Calendar.getInstance();
        //失效的时间
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        long fl = ca.getTimeInMillis();
        return fl;
    }

    public static void main(String[] args) {
        Calendar ca = Calendar.getInstance();
        //失效的时间
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        long fl = ca.getTimeInMillis();
        System.out.println(fl / 1000);
    }

}