package com.sc.ms.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: shichao
 * @date: 2019/6/15
 * @description: 公平锁Demo
 */
public class ReenterLockDemo2 implements  Runnable {

    private static ReentrantLock reentrantLock = new ReentrantLock(true);
    @Override
    public void run() {
        while (true){
            try {
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName()+"get Lock....");
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReenterLockDemo2 reenterLockDemo2 = new ReenterLockDemo2();

        Thread t1 = new Thread(reenterLockDemo2,"t1");
        Thread t2 = new Thread(reenterLockDemo2,"t2");
        t1.start();
        t2.start();
    }
}
