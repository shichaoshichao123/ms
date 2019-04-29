package com.sc.ms.juc.sctp.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/26
 * @description: 线程池示例
 * 在Java中使用Executor接口来定义线程池的最顶层接口
 * 线程池的底层就是ThreadPoolExecutor
 * Executors 是一个线程池的辅助工具类，用于对线程池的辅助操作
 *
 * 线程池的七大基本参数：
 * 1：corePoolSize：线程池核心线程数量，也就是线程池的最初始化的核心线程数。
 * 2：maximumPoolSize：线程池能容纳最多线程数，也就是线程池满负荷下最大线程数，在核心线程被占满了并且工作队列也满了的情况下或进行线程扩容直到数量到Max。
 * 3：keepAliveTime：线程池中多余的空闲线程（当线程池数量大于核心线程数且有线程空闲时间大于指定时间，这些空闲线程将会被回收直到线程数达到核心线程数就才维持）保持存活的时间。
 * 4：unit：这是呼应上面存活时间的单位。
 * 5：workQueue：提交到线程池的工作队列，该队列是一个阻塞队列（候客区）。
 * 6：threadFactory：线程工厂类用于生产工作线程的，一般用默认的参数就行。
 * 7：handler：线程池满后的拒绝策略用于线程池在最大线程数下运行并且阻塞队列也满了的情况下的处理方式。
 *
 * 线程池底层工作原理：
 *  基于7大参数进行理解（基于银行柜台办理业务去理解）
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        //创建固定线程数的线程池 一池固定线程 适合用用于长期任务
        //ExecutorService executorService = Executors.newFixedThreadPool(5);
        // 创建单线程数的线程池 一池一线程 适合单一线程
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        //创建根据情况新建的线程数的池 一次N线程 适合短期异步时间段的任务
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < 9; i++) {
                executorService.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName() + "处理业务");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

