package com.sc.ms.juc.sctp.v2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 自定义的阻塞式的生产者消费者
 * 生产一个消费一个的模式，程序员不用干预。
 */
public class PcDemo3 {

    public static void main(String[] args) {
        ShareData3 shareData = new ShareData3(new ArrayBlockingQueue<>(3));
        new Thread(() -> {
            System.out.println("生产线程启动。。。。。");
            try {
                shareData.make();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            System.out.println("消费线程启动。。。。。");
            try {
                shareData.sale();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        shareData.down();
    }
}

class ShareData3 {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue;

    public ShareData3(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println("传入的类型：" + blockingQueue.getClass().getName());
    }

    /**
     * 生产
     *
     * @throws InterruptedException
     */
    public void make() throws InterruptedException {
        String data = "";
        boolean returnFlage;
        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            returnFlage = blockingQueue.offer(data, 1L, TimeUnit.SECONDS);

            if (returnFlage) {
                System.out.println("生产数据成功，商品："+data);
            } else {
                System.out.println("生产数据失败！");
            }

            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("商品已经停产。。。。。");
    }

    /**
     * 销售
     */
    public void sale() throws InterruptedException {
        String result = null;
        while (flag) {
            result = blockingQueue.poll(3L, TimeUnit.SECONDS);
            if (null == result || result.equalsIgnoreCase("")) {
                flag = false;
                System.out.println("拿不到货了，停止该商品的销售工作！");
                return;
            }
            System.out.println("卖出了产品：" + result);
        }
    }

    /**
     * 模拟下架商品
     */
    public void down() {
        System.out.println("工厂决定停产。。。。。");
        flag = false;
    }
}
