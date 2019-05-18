package com.sc.ms.jvm.classLoader;

import java.io.*;

/**
 * @author: shichao
 * @date: 2019/5/18
 * @description: 自定义类加载
 */
public class MyClassLoader extends ClassLoader {
    private String classPath;
    private String classLoaderName;

    public MyClassLoader(String classPath, String classLoaderName) {
        this.classPath = classPath;
        this.classLoaderName = classLoaderName;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result = loadClassData(name);
        return defineClass(name, result, 0, result.length);
    }

    /**
     * 用于获取目标类的byte数组
     *
     * @param name
     * @return
     */
    private byte[] loadClassData(String name) {
        if (null == name || "".equals(name)) {
            throw new IllegalArgumentException("目标类路径不允许为空！");
        }
        name = classPath + name + ".class";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            inputStream = new FileInputStream(new File(name));
            byteArrayOutputStream = new ByteArrayOutputStream();
            int n;
            while ((n = inputStream.read()) != -1) {
                byteArrayOutputStream.write(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
}
