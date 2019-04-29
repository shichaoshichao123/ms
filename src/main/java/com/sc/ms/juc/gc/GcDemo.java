package com.sc.ms.juc.gc;

/**
 * @author: shichao
 * @date: 2019/4/29
 * @description: 演示垃圾回收
 * JVM参数配置：-Xms10m -Xmx-10m -XX:+PrintGCDetails
 */
public class GcDemo {

    public static void main(String[] args) {
        System.out.println("开始演示GC.....");

        Byte[] bytes = new Byte[50 * 1024 * 1024];

        System.out.println("GC 演示结束.....");
    }
}
