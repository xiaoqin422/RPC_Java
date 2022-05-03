package com.study.nio;

import java.nio.ByteBuffer;

/**
 * 包名: com.study.nio
 * 类名: PutBufferDemo
 * 创建用户: 25789
 * 创建日期: 2022年04月29日 10:09
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class PutBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        System.out.println("position" + buffer.position());//0 指针的索引位置
        System.out.println("capacity" + buffer.capacity());//10 最大用量
        System.out.println("remaining" + buffer.remaining());//10 - 0 = 10 剩余可用容量
        System.out.println("limit" + buffer.limit());//10 最大指针能到达的索引位置
        buffer.put("abcd".getBytes());
        System.out.println("================================");
        System.out.println("position" + buffer.position());//0 指针的索引位置
        System.out.println("capacity" + buffer.capacity());//10 最大用量
        System.out.println("remaining" + buffer.remaining());//10 - 0 = 10 剩余可用容量
        System.out.println("limit" + buffer.limit());//10 最大指针能到达的索引位置
    }
}