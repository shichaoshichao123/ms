package com.sc.ms.juc;

/**
 * @author: shichao
 * @date: 2019/6/15
 * @description: 基于双重检测的单例
 */
public class Singleton {


    /**
     * 禁止多线程下的指令重排
     */
    private volatile static Singleton singleton;

    private Singleton() {
    }

    public Singleton getSingleton() {
        if (singleton == null) {
            //同步
            synchronized (Singleton.class) {
                if (singleton == null) {
                    //由于创建对象的操作并不是原子性操作，所以需要进行禁止指令重排。
                    return new Singleton();
                }
            }
        }
        return singleton;
    }
}
