package com.sc.ms.juc.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: shichao
 * @date: 2019/7/6
 * @description: 信号量Demo
 */
public class SemaphoreDemo {
    private static final int THREAD_COUNT = 200;

    /**
     * 演示信号量控制并发，不过不会丢弃请求
     */
    private static void semaphore1() {
        Semaphore semaphore = new Semaphore(10 );
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire(2);
                    doSomething();
                    semaphore.release(2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();

    }

    /**
     * 演示同一时间内如果超过吞吐，任务将被丢弃
     *
     */
    private static void semaphore2() {
        Semaphore semaphore = new Semaphore(10 );
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire()){
                        doSomething();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();

    }

    private static void doSomething() throws Exception {
        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() +"-Thread :" + Thread.currentThread().getName() + " do something.....");

    }

    public static void main(String[] args) {
        SemaphoreDemo.semaphore2();
    }
}
