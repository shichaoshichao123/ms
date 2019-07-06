package com.sc.ms.juc.aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: shichao
 * @date: 2019/7/6
 * @description: CyclicBarrierDemo
 */
public class CyclicBarrierDemo {
    private static final int THREAD_COUNT = 10;

    private static CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(5,()->{
        System.out.println("哈哈哈哈😄");
    });

    private static void doCyclicBarrierDemo() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread.sleep(1000);
            executorService.execute(() -> {

                try {
                    doSomething();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

    }

    /**
     * 带runnbale的方法
     * @throws Exception
     */
    private static void doCyclicBarrierDemo2() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread.sleep(1000);
            executorService.execute(() -> {

                try {
                    doSomething();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

    }

    private static void doSomething() throws Exception {
        Thread.sleep(100);
        System.out.println("Thread :" + Thread.currentThread().getName() + " do something.....");
        CYCLIC_BARRIER.await();
        System.out.println("Thread :" + Thread.currentThread().getName() + " do continue.....");
    }

    public static void main(String[] args) throws Exception {
        CyclicBarrierDemo.doCyclicBarrierDemo();
    }
}
