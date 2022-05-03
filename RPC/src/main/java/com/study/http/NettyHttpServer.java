package com.study.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 包名: com.study.http
 * 类名: NettyHttpServer
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 14:16
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyHttpServer {
    private final int PORT;

    public NettyHttpServer(int PORT) {
        this.PORT = PORT;
    }

    public void run() {
        EventLoopGroup bossGroup = null;
        EventLoopGroup workGroup = null;
        ServerBootstrap bootstrap = null;
        try {
            bossGroup = new NioEventLoopGroup(1);
            workGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //添加http编解码器
                            socketChannel.pipeline().addLast(new HttpServerCodec());
                            socketChannel.pipeline().addLast(new NettyHttpHandle());
                        }
                    });
            ChannelFuture future = bootstrap.bind(PORT);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("端口绑定成功");
                    } else {
                        System.out.println("端口绑定失败!!!");
                    }
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyHttpServer(8080).run();
    }
}