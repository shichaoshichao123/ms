package com.sc.ms.jvm.classLoader;

/**
 * @author: shichao
 * @date: 2019/5/18
 * @description: 用于测试自定义类加载的测试类
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("/Users/shichao/Study/clTest/", "myClassLoader");

        Class c = myClassLoader.findClass("TestClass");
        System.out.println(c.getClassLoader());
        c.newInstance();
    }
}
