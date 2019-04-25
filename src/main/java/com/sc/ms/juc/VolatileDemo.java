package com.sc.ms.juc;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/23
 * @description: volatile 是一种轻量级的同步机制。
 * 三大特性：保证可见效，不保证原子性，禁止指令重排。
 * 基本遵守了JMM规范
 */
public class VolatileDemo {

    public static void main(String[] args) {
        noAtomicity();
    }

    /**
     * volatileDemo 不保证原子性
     */
    private static void noAtomicity() {
        TestData testData = new TestData();
        for (int i = 0; i < 20; i++) {

            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getName() + " 开始操作testData...");
                for (int j = 0; j < 1000; j++) {
                    testData.increment();

                }
                System.out.println("线程" + Thread.currentThread().getName() + " 操作testData结束...新值：" + testData.num);
            }, "thread " + i).start();
        }
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("final value:" + testData.num);
    }

    /**
     * 保证可见性
     */
    private static void visibility() {
        TestData testData = new TestData();

        new Thread(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + " 开始操作testData...");
            try {
                TimeUnit.SECONDS.sleep(3);
                testData.addTo100();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程" + Thread.currentThread().getName() + " 操作testData结束...新值：" + testData.num);
        }, "A").start();

        while (testData.num == 0) {

        }

        System.out.println("testData 的num值并被更新为：" + testData.num);
    }
}

/**
 * 模拟数据
 */
class TestData {
    volatile int num = 0;

    public void addTo100() {
        this.num = new Random().nextInt(100);
    }

    public  void increment() {
        num++;
    }
}
