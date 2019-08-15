package com.sc.ms.tomcat;

import java.io.File;

/**
 * @author: shichao
 * @date: 2019/8/15
 * @description:
 */
public class TomcatServer {

    public static void main(String[] args) {
        //目标类路径
        String classPath = System.getProperty("user.dir") + File.separator + "target"+File.separator+"classes";
        //创建Tomcat实例

        System.out.println(classPath);
    }
}
