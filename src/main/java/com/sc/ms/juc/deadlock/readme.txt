                    内容简介


该文件夹下是关于死锁排查的知识复盘

查看死锁方式(两步走)：
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