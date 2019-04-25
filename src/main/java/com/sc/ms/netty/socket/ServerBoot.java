package com.sc.ms.netty.socket;

/**
 * @author: shichao
 * @date: 2019/4/21
 * @description:
 */
public class ServerBoot {

    private static final int PORT = 8888;

    public static void main(String[] args) {
        Server server = new Server(PORT);

        server.start();
    }
}
