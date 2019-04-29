package com.sc.ms.juc.deadlock;

import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/29·
 * @description: 死锁示例
 * <p>
 * 死锁原因：
 * 两个或多个线程在执行期间因争抢同一个资源导致等待，若无外力干涉不能退出的情况
 *
 * 查看死锁方式(两步走)：
 * 1：jps：java版的Ps用于查看进程 如：jps -l 查看正在运行的进程
 *
 * yingqideMacBook-Pro:~ shichao$ jps -l
 * 21936 org.jetbrains.jps.cmdline.Launcher
 * 21937 com.sc.ms.juc.deadlock.DeadLockDemo1
 * 20836 org.jetbrains.jps.cmdline.Launcher
 * 9316
 * 19654 org.jetbrains.jps.cmdline.Launcher
 * 2：jStack： 用命令 jstack 加进程编号 查看进程堆栈信息 如：jstack 21937
 *
 * Found one Java-level deadlock:
 * =============================
 * "B":
 *   waiting to lock monitor 0x00007f832b8260b8 (object 0x000000079571d888, a java.lang.Object),
 *   which is held by "A"
 * "A":
 *   waiting to lock monitor 0x00007f832b8289f8 (object 0x000000079571d898, a java.lang.Object),
 *   which is held by "B"
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "B":
 * 	at com.sc.ms.juc.deadlock.LockHold.run(DeadLockDemo1.java:61)
 * 	- waiting to lock <0x000000079571d888> (a java.lang.Object)
 * 	- locked <0x000000079571d898> (a java.lang.Object)
 * 	at java.lang.Thread.run(Thread.java:748)
 * "A":
 * 	at com.sc.ms.juc.deadlock.LockHold.run(DeadLockDemo1.java:48)
 * 	- waiting to lock <0x000000079571d898> (a java.lang.Object)
 * 	- locked <0x000000079571d888> (a java.lang.Object)
 * 	at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 */
public class DeadLockDemo1 {

    public static void main(String[] args) {

        new Thread(new LockHold(1),"A").start();
        new Thread(new LockHold(2),"B").start();
    }
}

class LockHold implements Runnable {

    private static Object lockA = new Object();
    private static Object lockB = new Object();

    private int flag ;

    public LockHold(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag == 1) {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + " 拥有lockA 现在尝试获取lockB");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + " 拥有lockB 现在尝试获取lockA");

                }
            }
        } else {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " 拥有lockB 现在尝试获取lockA!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName() + " 拥有lockA 现在尝试获取lockB!");

                }
            }
        }

    }
}
