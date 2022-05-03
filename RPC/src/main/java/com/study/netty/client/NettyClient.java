package com.study.netty.client;

import com.study.netty.config.MessageCoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 包名: com.study.netty.client
 * 类名: NettyClient
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 12:52
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //1.创建线程组
        EventLoopGroup group = new NioEventLoopGroup();
        //2.创建客户端启动助手
        Bootstrap bootstrap = new Bootstrap();
        //3.设置线程组
        bootstrap.group(group)
                .channel(NioSocketChannel.class)//4.设置客户端通道实现类
                .handler(new ChannelInitializer<SocketChannel>() {//5.常见通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MessageCoder());//添加编解码器
                        socketChannel.pipeline().addLast(new NettyClientHandle());
                    }
                });
        //7.启动客户端，等待连接服务端，将异步改为同步
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", 9999)).sync();
        System.out.println("客户端启动成功...");
        //8.关闭通道和关闭连接池
        future.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}