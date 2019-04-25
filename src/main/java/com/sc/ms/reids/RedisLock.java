package com.sc.ms.reids;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * @author: shichao
 * @date: 2019/3/21
 * @description:
 */
public class RedisLock {

    private static Jedis jedis;

    /**
     * 加锁
     *
     * @param key  需要获取锁的key
     * @param time 拿锁的超时时间，超过了就不再尝试
     * @return 获取成功的情况放回获取的锁值， 获取失败直接返回nul
     */
    public static String lock(String key, Long time) {
        if (null == key || "".equals(key)) {
            throw new RuntimeException("获取分布式锁失败");
        }
        try {
            jedis = RedisManager.getJedis();
            Long endTime = System.currentTimeMillis() + time;
            String value = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < endTime) {
                if (jedis.setnx(key, value) == 1) {
                    return value;
                }
                if (jedis.ttl(key) == -1) {
                    jedis.expire(key, 10);
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("获取分布式锁失败");
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean unLock(String key, String value) {
        try {
            //用于进行事务失效
            jedis = RedisManager.getJedis();
            while (true) {
                jedis.watch(key);
                if (jedis.get(key).equals(value)) {
                    //进行事务处理
                    Transaction transaction = jedis.multi();
                    transaction.del(key);
                    List<Object> result = transaction.exec();
                    if (null == result) {
                        continue;
                    }
                    return true;
                }
                jedis.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("释放分布式锁失败");
        }
        return false;
    }
}
