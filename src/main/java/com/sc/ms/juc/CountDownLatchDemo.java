package com.sc.ms.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: CountDownLatch demo
 *
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            final int a = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 国被灭！");
                countDownLatch.countDown();
            }, CountyEnum.forEachByIndex(i).getName()).start();
        }
        countDownLatch.await();
        System.out.println("秦国灭六国统一中华!");
    }
}
