package com.sc.ms.reids;

/**
 * @author: shichao
 * @date: 2019/3/21
 * @description:
 */
public class RedisTest {

    public static void main(String[] args) {
        String value = RedisLock.lock("qqq", 10000L);
        if (null != value) {
            System.out.println("获取分布式锁成功,value = " + value);
        } else {
            System.out.println("获取分布式锁失败,value = " + value);

        }
    }
}
