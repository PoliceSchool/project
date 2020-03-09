package com.atguigu.nio.how_to_use;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * 参考链接:https://www.bilibili.com/video/av35956039?p=2
 * 一.缓冲区:在Java NIO中负责数据的存取缓冲区就是数组.用于存储不同数据类型的数据.
 * 根据数据类型不同(除了boolean),提供了相应类型的缓冲区:
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * 上述缓冲区的管理方式几乎一致,通过allocate()获取缓冲区
 * <p>
 * 二.缓冲区存取数据的两个核心方法:
 * put():存入数据到缓冲区中
 * get():获取缓冲区中的数据
 * <p>
 * 三.缓冲区中的四个核心属性:
 * capacity: 容量,表示缓冲区中最大存储数据的容量.一旦声明不能改变.(因为是数组的长度)
 * limit:  界限,表示缓冲区中可以操作数据的大小.(limit后边的数据不能进行读写)
 * position: 位置,表示缓冲区中当前正在操作数据的位置.
 * position <= limit <= capacity
 * mark: 标记,表示记录当前position的位置.可以通过reset()恢复到刚刚mark()的位置
 * <p>
 * 四.直接缓冲区与非直接缓冲区
 * 非直接缓冲区: 通过allocate()方法分配缓冲, 将缓冲区建立在JVM的内存中
 * 直接缓冲区:通过allocateDirect()方法分配直接缓冲区,将缓冲区建立在物理内存中.可以提高效率
 */

public class TestBuffer {

    @Test
    public void test1() {
        // 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-----------allocate()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 利用put()存入数据到缓冲区中
        String str = "abcde";
        buf.put(str.getBytes());
        System.out.println("-----------after put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 利用flip()切换成读取数据模式
        buf.flip();
        System.out.println("-----------after flip()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 利用get()读取数据
        System.out.println("-----------get()----------------");
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        // 读取完数据
        System.out.println("-----------after get()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 重读缓冲区的数据
        System.out.println("-----------after rewind()----------------");
        buf.rewind();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        // 清空缓冲区,但是缓冲区的数据依然存在,因为只是初始化指针位置,缓冲区的数据没被清空
        System.out.println("-----------after clear()----------------");
        buf.clear();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
    }

    @Test
    public void test2() {
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));

        System.out.println(buf.position());
        // mark():标记
        buf.mark();
        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        // reset():恢复到mark的位置
        buf.reset();
        System.out.println(buf.position());

        // 判断缓冲区中是否还有剩余数据
        if (buf.hasRemaining()) {
            // 获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test3() {
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }
}
