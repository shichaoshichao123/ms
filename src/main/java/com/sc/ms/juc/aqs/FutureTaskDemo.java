package com.sc.ms.juc.aqs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: shichao
 * @date: 2019/7/6
 * @description: FutureTask demo
 */
public class FutureTaskDemo {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("Do future teak.....");
            Thread.sleep(5000);
            return "Done";
        });

        new Thread(futureTask).start();
        System.out.println("main thread do something.....");
        Thread.sleep(2000);

        System.out.println(futureTask.get());

    }
}
