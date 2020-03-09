package com.atguigu.nio.theory_of_nio.trandition_io_with_multi_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 多线程传统IO过程
 */
public class MyServer implements Runnable {
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(9876));
            while (true) {
                System.out.println("等待客户端连接,阻塞中...");
                // accept方法专门负责通信;会被阻塞,同时程序释放CPU资源
                Socket socket = serverSocket.accept();
                new Thread(new MySocket(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MySocket implements Runnable {
        private Socket socket;

        MySocket(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                byte[] bs = new byte[1024];
                System.out.println("连接成功!等待接收数据, 线程" + Thread.currentThread().getId() + "阻塞中...");
                int read = socket.getInputStream().read(bs);
                System.out.println("读取数据成功!, 线程" + Thread.currentThread().getId() + " 打印消息: " + new String(bs, 0, read));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new MyServer()).start();
    }
}
