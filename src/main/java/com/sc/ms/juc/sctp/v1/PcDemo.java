package com.sc.ms.juc.sctp.v1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 两个线程一个加一个减
 * 生产消费
 * 使用 ReentrantLock 的 Condition 控制！
 */
public class PcDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.add();
            }

        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareData.add();
            }

        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                shareData.reduce();
            }
        }, "C").start();
    }
}

class ShareData {
    private int num = 0;
    ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void add() {
        lock.lock();
        try {
            while (num != 0) {
                condition.await();
            }
            num++;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + " 增加num！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void reduce() {
        lock.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + " 减少num！");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
