package com.sc.ms.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: shichao
 * @date: 2019/4/23
 * @description: CAS 示例
 */
public class CasDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println( atomicInteger.compareAndSet(5,200));
        System.out.println(atomicInteger.get());
        System.out.println( atomicInteger.compareAndSet(200,233));
        System.out.println(atomicInteger.get());

    }
}
