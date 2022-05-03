package com.study.chat.server;

import com.study.chat.config.MessageCoder;
import com.study.chat.server.handler.NettyChatServerHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 包名: com.study.chat
 * 类名: NettyChatServer
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:15
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyChatServer {
    private final int port;

    public NettyChatServer(int port) {
        this.port = port;
    }

    public void run(){
        EventLoopGroup bossGroup = null;
        EventLoopGroup workGroup = null;
        ServerBootstrap bootstrap = null;
        try {
            bossGroup = new NioEventLoopGroup(1);
            workGroup = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MessageCoder());//添加编解码器
                            socketChannel.pipeline().addLast(new NettyChatServerHandle());//添加业务处理器
                        }
                    });
            ChannelFuture future = bootstrap.bind(port);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        System.out.println("端口绑定成功");
                    }else{
                        System.out.println("端口绑定失败！！！");
                    }
                }
            });
            System.out.println("聊天室服务端启动成功");
            //关闭通道
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyChatServer(9999).run();
    }
}