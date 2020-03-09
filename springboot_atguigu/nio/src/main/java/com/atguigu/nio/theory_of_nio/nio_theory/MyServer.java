package com.atguigu.nio.theory_of_nio.nio_theory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程传统IO过程
 */
public class MyServer implements Runnable {
    List<SocketChannel> list = new ArrayList<>();


    @Override
    public void run() {
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            ssc.bind(new InetSocketAddress(9876));
            ssc.configureBlocking(false);
            while (true) {
                SocketChannel socketChannel = ssc.accept();
                if (socketChannel == null) {
                    Thread.sleep(1000);
                    System.out.println("没人连接");
                    for (SocketChannel channel : list) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int k = channel.read(byteBuffer);
                        System.out.println("没人连接, 需要读取的字节数:" + k);
                        if (k != 0) {
                            byteBuffer.flip();
                            System.out.println("没人连接, " + new String(byteBuffer.array(), 0, k));
                        }
                    }
                } else {
                    socketChannel.configureBlocking(false);
                    list.add(socketChannel);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    // 得到套接字,循环所有套接字,通过套接字获取数据
                    for (SocketChannel channel : list) {
                        int k = channel.read(byteBuffer);
                        System.out.println("有人连接, 需要读取的字节数:" + k);
                        if (k != 0) {
                            byteBuffer.flip();
                            System.out.println("有人连接, " + new String(byteBuffer.array(), 0, k));
                        }
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new MyServer()).start();
    }
}
