package com.sc.ms.juc.jvm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: shichao
 * @date: 2019/6/4
 * @description:
 */
public class MyProfiler {

    public static ConcurrentHashMap<Class<?>, AtomicInteger> data = new ConcurrentHashMap<>();

    public static void fireAllocationEvent(Class<?> klass) {
        data.computeIfAbsent(klass, kls -> new AtomicInteger())
                .incrementAndGet();
    }

    public static void dump() {
        data.forEach((kls, counter) -> System.err.printf("%s: %d\n", kls.getName(), counter.get()));
    }

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(MyProfiler::dump));
    }
}
