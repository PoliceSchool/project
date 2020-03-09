package com.atguigu.nio.how_to_use;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 测试在服务端BIO程序阻塞时,其他工作线程仍在工作
 */
public class TestBlockingNIO3 {

    // 客户端
    @Test
    public void client() throws IOException, InterruptedException {
        // 1. 获取网络通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            buf.put(("来自客户端的信息" + i).getBytes());
            // 4. 读取本地文件并发送到服务端
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        // 关闭通道
        sChannel.close();
    }

    // 服务端
    @Test
    public void server() throws IOException {
        new MyThread2().start();
        System.out.println("上面那句代码是模拟其他线程在执行任务, 也就是模拟其他线程跟server的阻塞IO程序在强占CPU资源");
        // 1. 获取网络通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        while (true) {
            // 3.获取客户端连接的通道(阻塞等待远程连接)
            SocketChannel sChannel = ssChannel.accept();
            // 4. 接收客户端数据
            ByteBuffer buf = ByteBuffer.allocate(1024);
            System.out.println();
            int len = 0;
            while ((len = sChannel.read(buf)) != -1) {
                buf.flip();
                System.out.println("server端的BIO线程" + Thread.currentThread().getId() + "工作了" + "," + new String(buf.array(), 0, len));
                buf.clear();
            }
        }
    }

    class MyThread1 extends Thread {
        @Override
        public void run() {
            System.out.println("其他工作线程" + Thread.currentThread().getId() + "工作了");
        }
    }

    class MyThread2 extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new MyThread1().start();
            }
        }
    }
}
