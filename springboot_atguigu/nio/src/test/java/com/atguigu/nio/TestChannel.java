package com.atguigu.nio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 参考连接:https://www.bilibili.com/video/av35956039?p=6
 * 一.通道(Channel):用于源节点与目标节点的连接.
 * 在Java NIO中负责缓冲区中数据的传输.Channel本身不存储数据,因此余姚配合缓冲区进行传输.
 *
 * 二. 通道的主要实现类
 * java.nio.channels.Channel 接口:
 *      |--FileChannel :用于本地数据传输
 *      |--SocketChannel :用于网络传输,基于TCP协议
 *      |--ServerSocketChannel :用于网络传输,基于TCP协议
 *      |--DatagramChannel :用于网络传输,基于UDP协议
 *
 * 三.获取通道
 * 1. Java针对支持通道的类提供了getChannel()方法
 *      本地IO:
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络IO:
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 * 2. 在JDK1.7中的NIO2针对各个通道提供了静态方法open()
 * 3. 在JDK1.7中的NIO2的Files工具类的newByteChannel()
 *
 * 四.通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 * 五. 分散(Scatter)与聚集(Gather)
 * 分散读取(Scattering Reads) : 将通道中的数据分散到多个缓冲区中
 * 聚集写入(Gathering Writes) : 将多个缓冲区中的数据聚集到通道中
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestChannel {

    // 利用通道完成文件的复制(非直接缓冲区)
    // idea总大小673.6MB, 使用此方法复制第一次耗时1164毫秒,第二次1254毫秒
    @Test
    public void test1() throws IOException {
        long start = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream("/home/jimson/temp/ideaIC-2019.2.3.tar.gz");
        FileOutputStream fos = new FileOutputStream("/home/jimson/temp/1.ideaIC-2019.2.3.tar.gz");

        // 获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        // 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        // 将通道中的数据存入缓冲区中
        while (inChannel.read(buf) != -1) {
            // 切换成读取数据模式
            buf.flip();
            // 将缓冲区中的数据写入通道中
            outChannel.write(buf);
            // 清空缓冲区
            buf.clear();
        }
        inChannel.close();
        outChannel.close();
        fis.close();
        fos.close();
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为:" + (end - start));
    }

    // 利用通道完成文件的复制(直接缓冲区, 内存映射文件, 只有ByteBuffer支持直接缓冲区)
    // idea总大小673.6MB, 使用此方法复制第一次耗时616毫秒,第二次548毫秒
    @Test
    public void test2() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("/home/jimson/temp/ideaIC-2019.2.3.tar.gz"), StandardOpenOption.READ);
        FileChannel outChnnel = FileChannel.open(Paths.get("/home/jimson/temp/2.ideaIC-2019.2.3.tar.gz"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        // 内存映射文件(本质上还是new了一个DirectByteBuffer出来,跟ByteBuffer.allocateDirect()作用一样)
        // 这种方式创建出来的ByteBuffer存放在物理内存中,没有放在Java堆中;
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChnnel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作(这里已经不需要对inChannel和outChnnel进行读写了,直接把数据放在缓冲区里面即可,
        // 因为已经把数据写入物理内存了,接下来写入文件就是操作系统做的事了)
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChnnel.close();
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为:" + (end - start));
    }

    // 通道之间的数据传输(直接缓冲区)
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("/home/jimson/temp/ideaIC-2019.2.3.tar.gz"), StandardOpenOption.READ);
        FileChannel outChnnel = FileChannel.open(Paths.get("/home/jimson/temp/2.ideaIC-2019.2.3.tar.gz"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        inChannel.transferTo(0, inChannel.size(), outChnnel);
        outChnnel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChnnel.close();
    }

    @Test
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("/home/jimson/temp/1.txt", "rw");

        // 1. 获取通道
        FileChannel channel1 = raf1.getChannel();

        // 2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        // 4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("/home/jimson/temp/2.txt","rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }
}
