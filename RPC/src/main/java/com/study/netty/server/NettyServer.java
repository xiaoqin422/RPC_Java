package com.study.netty.server;

import com.study.netty.config.MessageCoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 包名: com.study.netty.server
 * 类名: NettyServer
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 12:34
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //1 Main Reacotor处理客户端连接请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //2.创建workGroup线程组：处理网络事件-读写事件  默认线程数2*处理器数
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //3.创建服务端启动助手
        ServerBootstrap bootstrap = new ServerBootstrap();
        //4.设置线程组
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)//5。设置服务端通道实现类
                .option(ChannelOption.SO_BACKLOG,128)//6.参数设置-设置线程队列中等待连接的个数
                .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)//7.参数设置-设置活跃状态，child是设置workGroup
                .childHandler(new ChannelInitializer<SocketChannel>() { //8.创建通道初始化对象
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MessageCoder());//添加编解码器
                        //9.向pipeline中添加自定义业务处理handle
                        socketChannel.pipeline().addLast(new NettyServerHandle());
                    }
                });
        //10.启动服务端并绑定端口，同时将异步改为同步
        ChannelFuture future = bootstrap.bind(9999).sync();
        System.out.println("服务器启动成功....");
        //11.关闭通道（并不是真正意义上的关闭，而是监听通道关闭状态)和关闭连接池
        future.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}