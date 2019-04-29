package com.sc.ms.juc.jvm;

import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/4/29
 * @description: 用于查看JVM默认参数
 * <p>
 * 3.3:查看JVM是否开启了某种选项：如查看是否开启了GCDetails
 * 1：jps获取指定进程号
 * 2：使用jinfo -flag 参数类型 进程号 查看JVM是否开启了相关属性
 * jinfo -flag PrintGCDetails 22862 获取PrintGCDetails状态
 * jinfo -flag MetaspaceSize 23098 查看MetaSpaceSize的大小
 */
public class JvmParamsDemo {

    public static void main(String[] args) {
        System.out.println("Hello GC ----------------------");
        printJVMParams();
        try {
            TimeUnit.SECONDS.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 打印JVM相关参数值
     */
    public static void printJVMParams() {

        System.out.println(Runtime.getRuntime().maxMemory());

    }
}
