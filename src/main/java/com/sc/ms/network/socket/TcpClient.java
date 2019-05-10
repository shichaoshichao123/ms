package com.sc.ms.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author: shichao
 * @date: 2019/5/8
 * @description:
 */
public class TcpClient {

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8888);
                byte[] buff = new byte[1024];
                int len;
                while (true) {
                    System.out.println("客户端发送数据.....");
                    String message = "客户端消息" + new Random().nextInt();
                    socket.getOutputStream().write(message.getBytes());
                    if ((len = socket.getInputStream().read(buff)) != -1) {
                        String result = new String(buff, 0, len);
                        System.out.println("客户端接收数据.....:" + result);
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
