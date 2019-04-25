package com.sc.ms.juc;

/**
 * @author: shichao
 * @date: 2019/4/23
 * @description: 单例模式
 */
public class SingletonDemo {

    /**
     * 声明禁止指令重排变量
     */
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + " 构造方法被调用啦。。。");
    }

    /**
     * 懒汉模式
     *
     * @return 单例对象
     */
    public static SingletonDemo getInstance1() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    /**
     * 同步懒汉模式（代价高不推荐）
     *
     * @return 单例对象
     */
    public static synchronized SingletonDemo getInstance2() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    /**
     * 双重检查机制 DCL（推荐）
     * 要注意多线程下的指令重排对线程安全的影响
     *
     * @return 单例对象
     */
    public static SingletonDemo getInstance3() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 80; i++) {
            new Thread(() -> {
                System.out.println("线程：" + Thread.currentThread().getName());
                SingletonDemo.getInstance3();

            }, String.valueOf(i)).start();
        }

    }

}
