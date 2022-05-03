package com.study.selector;

import com.sun.xml.internal.bind.v2.runtime.output.DOMOutput;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * 包名: com.study.selector
 * 类名: NIOSelectorServer
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 8:23
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NIOSelectorServer {
    public static void main(String[] args) throws IOException {
        //1.打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //3.默认是阻塞的，设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //4.创建选择器
        Selector selector = Selector.open();
        //5.将服务端通道注册到选择器上,并指定注册监听的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功！");
        while (true){
            //6.检查选择器是否有事件
            int select = selector.select(2000);
            if (select == 0){
                continue;
            }
            //7.获取事件合集
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                //8.判断事件是否是客户端连接事件
                if (key.isAcceptable()){
                    //9.得到客户端连接通道，设置为非阻塞，并将其注册到选择器上，指定监听事件
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端已连接......"  + socketChannel);
                    //必须设置为非阻塞，选择器需要轮询监听每一个通道的事件
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                //10.判断事件是否为客户端读就绪事件
                if (key.isReadable()){
                    //11.得到客户端连接通道，读取数据到缓冲区
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = socketChannel.read(buffer);
                    if (len > 0){
                        System.out.println("接收到客户端发送的消息:" + new String(buffer.array(),
                                0,len, StandardCharsets.UTF_8));
                        //12.服务端应答，结束后释放资源
                        socketChannel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
                        socketChannel.close();
                    }
                }
                //13.从集合中删除对应的事件，防止二次处理
                iterator.remove();
            }
        }
    }
}