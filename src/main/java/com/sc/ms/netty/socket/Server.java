package com.sc.ms.netty.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/4/21
 * @description: Socket 服务端
 */
public class Server {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Socket 服务端启动完毕.....");

        } catch (IOException e) {
            System.out.println("Socket 服务端启动失败，原因：" + e.getMessage());
        }
    }

    public void start() {

        new Thread(() -> {
            doStart();
        }).start();
    }

    private void doStart() {
        while (true) {
            try {
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {

                System.out.println("socket服务端出现异常，原因：" + e.getMessage());
            }
        }
    }


}
