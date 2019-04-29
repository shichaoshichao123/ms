package com.sc.ms.juc.jvm;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author: shichao
 * @date: 2019/4/29
 * @description:
 * 若引用和虚引用在GC完成之前会被放置到引用队列中临时保存一下，以这种机制实现在对象被回收之前做一些事情！
 */
public class ReferenceQueueDemo {

    public static void main(String[] args) {

        Object o = new Object();

        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        WeakReference<Object> weakReference = new WeakReference<>(o,referenceQueue);

        System.out.println(o);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
        System.out.println();
        System.out.println("--------------------");
        o=null;
        System.gc();
        System.out.println(o);
        System.out.println(weakReference.get());
        System.out.println(referenceQueue.poll());
    }
}
