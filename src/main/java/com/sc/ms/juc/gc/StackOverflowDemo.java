package com.sc.ms.juc.gc;

/**
 * @author: shichao
 * @date: 2019/4/30
 * @description: 栈异常demo(方法调用层次深导致方法栈被撑爆了)，注意：这是错误而不是异常
 * 比较常见的是递归过深导致的
 */
public class StackOverflowDemo {

    public static void main(String[] args) {
            stackOverFlow();
    }

    private static void stackOverFlow(){
        stackOverFlow();
    }
}
