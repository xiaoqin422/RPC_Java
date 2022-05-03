package com.study.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 包名: com.study.channel
 * 类名: NIOClient
 * 创建用户: 25789
 * 创建日期: 2022年05月02日 21:36
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //1、打开通道
        SocketChannel socketChannel = SocketChannel.open();
        //2、设置连接
        socketChannel.connect(new InetSocketAddress("127.0.0.1",9999));
        //3、向服务端发送数据
        socketChannel.write(ByteBuffer.wrap("老板，还钱".getBytes(StandardCharsets.UTF_8)));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //4、接受服务端发送的数据
        int len = socketChannel.read(buffer);
        System.out.println("接收到服务端发送的消息:" + new String(buffer.array(),0,len));
        //5、释放资源
        socketChannel.close();
    }
}