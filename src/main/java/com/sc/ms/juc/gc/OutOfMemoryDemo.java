package com.sc.ms.juc.gc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/30
 * @description: 堆空间溢出异常Demo 注意这错误不是异常
 */
public class OutOfMemoryDemo {
    public static void main(String[] args) {
        metaspace();
    }

    /**
     * 在设计JVM最大堆内存为80m的情况下实现堆内存被占满
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.heapSpace(OutOfMemoryDemo.java:17)
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.main(OutOfMemoryDemo.java:10)
     */
    private static void heapSpace() {
        byte[] bytes = new byte[100 * 1024 * 1024];

    }

    /**
     * 该方法演示GC回收频繁导致的OOM
     * 复现条件：-XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m -Xmx20m
     * [Full GC (Ergonomics) [PSYoungGen: 5631K->5631K(6144K)] [ParOldGen: 13568K->13568K(13824K)] 19200K->19200K(19968K), [Metaspace: 3321K->3321K(1056768K)], 0.0525118 secs] [Times: user=0.17 sys=0.00, real=0.05 secs]
     * [Full GC (Ergonomics) Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
     * [PSYoungGen: 5631K->5631K(6144K)] [ParOldGen: 13570K->13570K(13824K)] 19202K->19202K(19968K), [Metaspace: 3321K->3321K(1056768K)], 0.0579237 secs] [Times: user=0.17 sys=0.01, real=0.06 secs]
     * [Full GC (Ergonomics) 	at java.lang.Integer.toString(Integer.java:403)
     */
    private static void gcHeadOver() {
        int i = 0;
        List list = new ArrayList();
        try {
            while (true) {
                list.add(String.valueOf(++i).intern());
            }
        } catch (Exception e) {
            System.out.println("==================" + i);
            e.printStackTrace();
        }
    }

    /**
     * 直接内存被耗尽导致的OOM，一般在NIO中比较常见
     * 复现环境：-XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m -Xmx20m
     * GC (System.gc()) [PSYoungGen: 1911K->496K(2560K)] 1911K->568K(9728K), 0.0013854 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [Full GC (System.gc()) [PSYoungGen: 496K->0K(2560K)] [ParOldGen: 72K->519K(7168K)] 568K->519K(9728K), [Metaspace: 3290K->3290K(1056768K)], 0.0047332 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
     * Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
     * at java.nio.Bits.reserveMemory(Bits.java:694)
     * at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:123)
     * at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:311)
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.directBuffer(OutOfMemoryDemo.java:55)
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.main(OutOfMemoryDemo.java:14)
     */
    private static void directBuffer() {

        ByteBuffer buff = ByteBuffer.allocateDirect(10 * 10024 * 1024);
    }

    /**
     * unable to create new native thread类型的OOM，不能创建更多的线程了。
     * <p>
     * ----------------2029
     * Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
     * at java.lang.Thread.start0(Native Method)
     * at java.lang.Thread.start(Thread.java:717)
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.nativeThread(OutOfMemoryDemo.java:80)
     * at com.sc.ms.juc.gc.OutOfMemoryDemo.main(OutOfMemoryDemo.java:15)
     * <p>
     * 从上面结果可以看出，Mac默认一个进程允许创建的最大线程数为2029个
     */
    private static void nativeThread() {
        for (int i = 1; ; i++) {
            System.out.println("----------------" + i);

            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 演示metaspace发生的OOM
     * -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m -Xmx10m -XX:MetaSpaceSize=10m
     */
    public static void metaspace() {
        for (int i = 1; ; i++) {
            try {
                System.out.println("---------------------" + i);

                new TestMetaSpace();
            } catch (Exception e) {
                System.out.println("---------------------" + i);
                e.printStackTrace();
            }
        }
    }

    static class TestMetaSpace {
        private static final String str= "123";
    }
}
