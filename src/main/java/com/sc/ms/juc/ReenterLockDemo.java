package com.sc.ms.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: shichao
 * @date: 2019/4/24
 * @description: 可重入锁demo，可重入锁的实现机制底层原理都是通过程序计数来完成的
 *
 */

public class ReenterLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(phone::sendSMS, "TA").start();
        new Thread(phone::sendSMS, "TB").start();

        Thread c = new Thread(phone);
        Thread d = new Thread(phone);

        c.start();
        d.start();
    }

    /**
     * 多线程操作的资源类
     */
    static class Phone implements Runnable {

        synchronized void sendSMS() {
            System.out.println(Thread.currentThread().getName() + " sendMSM.....");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendEmail();
        }

        synchronized void sendEmail() {
            System.out.println(Thread.currentThread().getName() + " sendEmail.....");

        }

        ReentrantLock lock = new ReentrantLock(false);

        @Override
        public void run() {
            get();
        }

        void set() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " set.....");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

        void get() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " get.....");
                set();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
