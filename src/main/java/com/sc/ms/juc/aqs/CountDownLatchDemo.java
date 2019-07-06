package com.sc.ms.juc.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/7/6
 * @description: 闭锁demo
 */
public class CountDownLatchDemo {
    private static final int THREAD_COUNT = 200;

    private static void doCountDownLatch1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT ; i++) {
            executorService.execute(() -> {
                try {
                    doSomething();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10,TimeUnit.MILLISECONDS);
        System.out.println("all task is done.....");
        executorService.shutdown();

    }

    private static void doSomething() throws Exception {
        Thread.sleep(100);
        System.out.println("Thread :" + Thread.currentThread().getName() + "do something.....");

    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo.doCountDownLatch1();
    }
}
