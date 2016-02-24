package com.bjesd.redis;


import java.io.Serializable;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class SpringRedisUtil {
 
    @SuppressWarnings("unchecked")
    private static RedisTemplate<Serializable, Serializable> redisTemplate = 
    /*
                (RedisTemplate<Serializable, Serializable>) ApplicationContextUtil
                       .getBean("jedisTemplate");
     */
    (RedisTemplate<Serializable, Serializable>) 
    new ClassPathXmlApplicationContext("classpath:applicationContext.xml").
    getBean("jedisTemplate");
     
    public static void save(final String key, Object value) {
    	
        final byte[] vbytes = SerializeUtil.serialize(value);
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(redisTemplate.getStringSerializer().serialize(key), vbytes);
                return null;
            }
        });
    }
 
    public static <T> T get(final String key, Class<T> elementType) {
        return redisTemplate.execute(new RedisCallback<T>() {
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keybytes = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keybytes)) {
                    byte[] valuebytes = connection.get(keybytes);
                    @SuppressWarnings("unchecked")
                    T value = (T) SerializeUtil.unserialize(valuebytes);
                    return value;
                }
                return null;
            }
        });
    }
}