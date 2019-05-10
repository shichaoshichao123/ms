package com.sc.ms.network.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author: shichao
 * @date: 2019/5/8
 * @description:
 */
public class UdpServer {

    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(8888);
            byte[] buff = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
            datagramSocket.receive(datagramPacket);

            //接收数据包
            byte[] result = datagramPacket.getData();
            //获取数据包中的内容
            String content = new String(result, 0, datagramPacket.getLength());
            System.out.println();
            //将要发送给客户端的数据转化成二进制
            byte[] sendData = String.valueOf(content.length()).getBytes();
            //通过数据源对象获取具体消息发送接信息，在进行数据包的封装
            DatagramPacket sendPackage = new DatagramPacket(sendData, sendData.length, datagramPacket.getAddress(), datagramPacket.getPort());
            //发送数据
            datagramSocket.send(sendPackage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
