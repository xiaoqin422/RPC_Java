package com.study.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * 包名: com.study.channel
 * 类名: NIOServer
 * 创建用户: 25789
 * 创建日期: 2022年05月02日 21:27
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //1、打开一个服务端的通道
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //2、绑定对应的端口号
        socketChannel.bind(new InetSocketAddress(9999));
        //3、通道默认是阻塞的，需要设置为非阻塞
        socketChannel.configureBlocking(false);
        System.out.println("服务端启动成功。。。。。。");
        while (true){
            //4、检查是否有客户端连接 有客户端连接会返回对应的channel
            SocketChannel accept = socketChannel.accept();
            if (accept == null){
                System.out.println("没有客户端连接...去做别的事情（非阻塞）");
                Thread.sleep(2000);
                continue;
            }
            //5、获取客户端传递过来的消息
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 0:没有读到有效字符串 -1：没有读完 正数：读取到的字节长度
            int len = accept.read(buffer);
            System.out.println("客户端消息：" + new String(buffer.array(),0,len));
            //6、给客户端写数据
            accept.write(ByteBuffer.wrap("服务端返回信息".getBytes(StandardCharsets.UTF_8)));
            //7、释放资源
            accept.close();
        }
    }
}