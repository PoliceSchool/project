package com.atguigu.nio.theory_of_nio.trandition_io_with_single_thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统IO过程
 */
public class MyServer implements Runnable {
    @Override
    public void run() {
        byte[] bs = new byte[1024];
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(9876));
            while (true) {
                System.out.println("等待客户端连接,阻塞中...");
                // accept方法专门负责通信;会被阻塞,同时程序释放CPU资源
                Socket socket = serverSocket.accept();
                System.out.println("连接成功!等待接收数据, 阻塞中...");
                // read方法会被阻塞,因为要等待客户端发送数据;
                // 如果客户端一直不发送数据,那么该线程就会一直堵塞,
                // 又因为此时服务端程序是单线程的,所以如果有新的客户端连接到来也无法处理,
                // 效率低下,因此可以使用多线程来处理,思路是当前线程用来监听是否有新的连接到来,
                // 若有,则为新的连接开辟一个新的线程去处理,即使新的连接会被阻塞,但是不妨碍当前线程监听新的连接
                // 相关代码在trandition_io_with_multi_thread包下
                int read = socket.getInputStream().read(bs);
                System.out.println("读取数据成功! 消息是: " + new String(bs, 0, read));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new MyServer()).start();
    }
}
