package com.atguigu.nio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一. 使用NIO完成网络通信的三个核心
 *  1. 通道(Channel): 负责连接
 *      java.nio.channels.Channel 接口:
 *          |-- SelectableChannel
 *          |-- ServerSocketChannel
 *          |-- DatagramChannel
 *          |-- Pipe.SinkChannel
 *          |-- Pipe.SourceChannel
 *
 *  2. 缓冲区(Buffer): 负责数据的存取
 *
 *  3. 选择器(Selector): 是SelectableChannel的多路复用器.用于监控SelectableChannel的IO状况(选择器只能用于网络IO,不能用于FileChannel)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBlockingNIO {

    // 客户端
    @Test
    public void client() throws IOException {
        // 1. 获取网络通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        // 2. 获取本地文件通道
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);
        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 读取本地文件并发送到服务端
        while (inChannel.read(buf) != -1) {
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }
        // 关闭通道
        inChannel.close();
        sChannel.close();
    }

    // 服务端
    @Test
    public void server() throws IOException {
        // 1. 获取网络通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        // 3.获取客户端连接的通道(阻塞等待远程连接)
        SocketChannel sChannel = ssChannel.accept();
        // 4. 接收客户端数据并保存到本地
        ByteBuffer buf = ByteBuffer.allocate(1024);
        FileChannel outChannel = FileChannel.open(Paths.get("2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        while (sChannel.read(buf) != -1) {
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }
        // 5. 关闭通道
        ssChannel.close();
        outChannel.close();
    }
}
