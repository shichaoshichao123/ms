package com.sc.ms.reids;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: shichao
 * @date: 2019/3/21
 * @description:
 */
public class RedisManager {

    private static JedisPool jedisPool;

    private RedisManager() {
    }

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
    }

    public static Jedis getJedis() {
        if (null != jedisPool) {
            return jedisPool.getResource();
        } else {
            throw new RuntimeException("初始化Redis连接失败");
        }

    }

}
