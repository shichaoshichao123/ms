package com.sc.ms.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: shichao
 * @date: 2019/4/24
 * @description: 自旋锁demo
 */
public class SpinLockDemo {
    /**
     * 利用原子引用线程的方式
     */
    AtomicReference<Thread> atomicReference = new AtomicReference();

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println("尝试获取锁的线程：" + thread.getName());
        while (!atomicReference.compareAndSet(null, thread)) {
        }
        System.out.println("线程：" + thread.getName() + "成功拿到了锁.....");

    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " 释放了锁.....");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "TA").start();

        try {
            //保证TA线程先启动
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "TB").start();

    }
}
