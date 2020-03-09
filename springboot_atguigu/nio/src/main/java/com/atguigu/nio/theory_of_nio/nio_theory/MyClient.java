package com.atguigu.nio.theory_of_nio.nio_theory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 多线程传统IO过程
 */
public class MyClient implements Runnable {
    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", 9876));
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入内容:");
            while (true) {
                String next = scanner.next();
                socket.getOutputStream().write(next.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new MyClient()).start();
    }
}
