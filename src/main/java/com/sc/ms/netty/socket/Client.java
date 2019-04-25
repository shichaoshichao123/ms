package com.sc.ms.netty.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/4/21
 * @description: Socket客户端
 */
public class Client {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;
    private static final int SLEEP_TIME = 3000;

    public static void main(String[] args) throws IOException {
        final Socket socket = new Socket(HOST, PORT);
        new Thread(() -> {
            System.out.println("客户端启动成功.....");
            while (true) {
                String message = "Hello,我是客户端" + Thread.currentThread().getName();
                try {
                    System.out.println("客户端发送数据.....");
                    socket.getOutputStream().write(message.getBytes());
                } catch (IOException e) {
                    System.out.println("客户端发送数据失败，原因：" + e.getMessage());
                }
                sleep();
            }

        }).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            System.out.println("线程休眠异常：" + e.getMessage());
        }

    }
}
