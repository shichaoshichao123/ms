package com.sc.ms.jvm;

/**
 * @author: shichao
 * @date: 2019/5/16
 * @description: 方法句柄的示例
 */
public class MethodHandles {

    public static void doTest(String name) {
        System.out.println(name);
    }

    public static void main(String[] args) throws Throwable {
//        MethodHandles.Lookup l = MethodHandles.lookup();
//        MethodType t = MethodType.methodType(void.class, Object.class);
//        MethodHandle mh = l.findStatic(Foo.class, "bar", t);
//        mh.invokeExact(new Object());
    }
}
