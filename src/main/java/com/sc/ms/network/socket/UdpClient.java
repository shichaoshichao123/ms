package com.sc.ms.network.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author: shichao
 * @date: 2019/5/8
 * @description:
 */
public class UdpClient {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            //目标地址
            InetAddress address = InetAddress.getByName("127.0.0.1");

            byte[] buff = "Hello UDP!".getBytes();
            //封装UDP数据包
            DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length, address, 8888);
            //发送数据包
            datagramSocket.send(datagramPacket);

            //接收服务端的数据
            byte[] data = new byte[1024];
            DatagramPacket datagramPacket1 = new DatagramPacket(data, data.length);
            datagramSocket.receive(datagramPacket1);
            String content = new String(datagramPacket1.getData(),0, datagramPacket1.getLength());
            System.out.println("接收服务端数据：" + content);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
