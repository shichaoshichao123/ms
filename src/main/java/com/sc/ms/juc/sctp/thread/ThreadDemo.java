package com.sc.ms.juc.sctp.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/26
 * @description: 新建线程的几种方式demo
 */
public class ThreadDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread t1 = new Thread(new T1());
        t1.start();
        FutureTask<String> futureTask = new FutureTask<>(new T2());
        Thread t2 = new Thread(futureTask);
        t2.start();
        while (!futureTask.isDone()) {
            //没干完，先干别的事
            System.out.println("没干完，先干别的事");
        }
        String s = futureTask.get();
        System.out.println(s);

    }


    static class T1 implements Runnable {

        @Override
        public void run() {
            System.out.println("T1运行啦！！！");
        }
    }

    /**
     * 通过Callable实现有返回值
     * FutureTask 主要用于解决并发和异步的问题
     * 可以实现ForkJoin的效果
     * 多个线程使用同一个FutureTask被多次调用只会执行一次哦，注意是同一个！
     */
    static class T2 implements Callable<String> {

        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(6);
            //看一下CPU核心数
            return Thread.currentThread().getName()+"CUP核数："+Runtime.getRuntime().availableProcessors();
        }
    }
}
