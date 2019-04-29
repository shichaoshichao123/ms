package com.sc.ms.juc.jvm;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * @author: shichao
 * @date: 2019/4/29
 * @description: 基于弱引用实现的WeakHashMap 解决缓存清空保证内存合理使用。
 */
public class WeakHashMapDemo {

    public static void main(String[] args) {
        myHashMap();
        System.out.println("====================");
        myWeakHashMap();
    }

    private static void myHashMap() {

        HashMap<Integer, String> hashMap = new HashMap<>(16);

        Integer key = 1;
        String value = "value";
        hashMap.put(key, value);
        System.out.println(hashMap);
        key = null;
        System.out.println(hashMap);
        System.gc();
        System.out.println(hashMap);
    }

    private static void myWeakHashMap() {

        WeakHashMap<Integer, String> hashMap = new WeakHashMap<>(16);

        Integer key = new Integer(2);
        String value = "value";
        hashMap.put(key, value);
        System.out.println(hashMap);
        key = null;

        System.out.println(hashMap);

        System.gc();
        System.out.println(hashMap);
    }
}
