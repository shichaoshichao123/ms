package com.sc.ms.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 信号量操作
 */
public class SemaphoreDemo {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(10);

        for (int i = 0; i < 30; i++) {
            final int temp = i;
            new Thread(() -> {
                int num = new Random().nextInt(3);
                try {
                    semaphore.acquire(++num);
                    System.out.println("用户" + temp + " 使用了" + num + " 个微波炉！");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(num);
                    System.out.println("用户" + temp + " 停止使用" + num + " 个微波炉！");
                }
            }).start();
        }
    }
}
