package com.sc.ms.juc.jvm;

/**
 * @author: shichao
 * @date: 2019/4/29
 * @description: 这里演示四种GCRoot
 * 1：虚拟机栈（栈帧中的局部变量，也叫局部变量表）中的引用对象
 * 2：方法区中类的静态属性引用的对象
 * 3：方法区中常量引用的对象
 * 4：本地方法栈中的JNI引用对象！
 */
public class GCRootDemo {

    //2：方法区中类的静态属性引用的对象
    //private static GCRootDemo3 gcRootDemo3;
    //3：方法区中常量引用的对象
    //private static final GCRootDemo3 gcRootDemo3 = new GCRootDemo3();
    public static void main(String[] args) {
        m();
    }

    public static void m() {
        GCRootDemo gcRootDemo = new GCRootDemo();
        System.out.println("虚拟机栈（栈帧中的局部变量，也叫局部变量表）中的引用对象");
    }
}
