package com.sc.ms.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 阻塞队列Demo
 * 阻塞时机：
 * 1：当队列数据为空的时候，从队列获取数据的操作将会被阻塞。
 * 2:当队列已满的时候，往队列里面添加数据的操作将被阻塞。
 * <p>
 * 使用扩展：
 * 1：阻塞队列是消息中间件的底层原理。
 * 2：可用于实现生产者消费者模式哦。
 * 3：线程池的底层实现
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

    }
}
