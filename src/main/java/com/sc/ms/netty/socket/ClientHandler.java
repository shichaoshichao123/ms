package com.sc.ms.netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/4/21
 * @description: 客户端处理类
 */
public class ClientHandler {
    private static final int MAX_DATA_LEN = 1024;
    private Socket socket;

    public ClientHandler(Socket client) {
        this.socket = client;
    }

    public void start() {
        new Thread(() -> {
            doStart();

        }).start();
    }

    /**
     * 用于处理客户端消息
     */
    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] buff = new byte[MAX_DATA_LEN];
            int len;
            while (true) {
                if ((len = (inputStream.read(buff))) != -1) {
                    String message = new String(buff, 0, len);
                    System.out.println("客户端说：" + message);
                    //将消息返回给客户端
                    socket.getOutputStream().write(buff);
                }
            }
        } catch (IOException e) {
            System.out.println("读取客户端消息异常,原因：" + e.getMessage());
        }
    }
}
