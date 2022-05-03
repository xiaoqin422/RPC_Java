package com.study.nio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;

/**
 * 包名: com.study.nio
 * 类名: BufferCreateDemo
 * 创建用户: 25789
 * 创建日期: 2022年04月29日 10:05
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class BufferCreateDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < 10; i++) {
            System.out.println(buffer.get());
        }
        buffer.position(0);
        buffer.put("abcdefghij".getBytes());
        buffer.flip();
        byte[] b = new byte[10];
        buffer.get(b);
        System.out.println(new String(b));

    }
}