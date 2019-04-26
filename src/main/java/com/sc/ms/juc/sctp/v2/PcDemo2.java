package com.sc.ms.juc.sctp.v2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 三个线程 A B C 要求 A输出三次1 接下来B输出5次2 再接下来C输出9次3 循环十轮
 */
public class PcDemo2 {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareData.runA();

            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareData.runB();

            }

        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                shareData.runC();

            }

        }, "C").start();
    }
}

class ShareData {
    private int num = 1;
    private ReentrantLock lock = new ReentrantLock();
    private Condition cA = lock.newCondition();
    private Condition cB = lock.newCondition();
    private Condition cC = lock.newCondition();

    public void runA() {
        lock.lock();
        try {
            while (num != 1) {

                cA.await();
            }
            for (int i = 0; i < 3; i++) {
                System.out.println(num);
            }
            num = 2;
            cB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void runB() {
        lock.lock();
        try {
            while (num != 2) {
                cB.await();

            }
            for (int i = 0; i < 5; i++) {
                System.out.println(num);
            }
            num = 3;
            cC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void runC() {
        lock.lock();
        try {
            while (num != 3) {
                cC.await();
            }
            for (int i = 0; i < 9; i++) {
                System.out.println(num);
            }
            num = 1;
            cA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}
