package com.sc.ms.network.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/5/8
 * @description:
 */
public class TcpServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (true) {
                Socket socket = serverSocket.accept();
                new TcpServerWorker(socket).start();
                System.out.println("有客户端接入..........");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
