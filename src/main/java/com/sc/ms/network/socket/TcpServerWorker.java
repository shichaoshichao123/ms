package com.sc.ms.network.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/5/8
 * @description:
 */
public class TcpServerWorker extends Thread {
    private Socket socket;

    public TcpServerWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while(true){
                if ((len = in.read(buff)) != -1) {
                    String message = new String(buff, 0, len);
                    System.out.println("收到客户端发来的数据内容：" + message);
                    out.write(String.valueOf(message.length()).getBytes());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
