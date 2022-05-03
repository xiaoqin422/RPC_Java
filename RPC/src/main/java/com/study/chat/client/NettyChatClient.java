package com.study.chat.client;

import com.study.chat.client.handle.NettyChatClientHandle;
import com.study.chat.config.MessageCoder;
import com.study.chat.server.NettyChatServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 包名: com.study.chat.client
 * 类名: NettyChatClient
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:33
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyChatClient {
    private final String IP;
    private final int PORT;

    public NettyChatClient(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
    }

    public void run(){
        EventLoopGroup group = null;
        Bootstrap bootstrap = null;
        try {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new MessageCoder());
                            socketChannel.pipeline().addLast(new NettyChatClientHandle());
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(IP, PORT)).sync();
            Channel channel = future.channel();
            System.out.println("--------------" +
                    channel.localAddress().toString().substring(1) + "--------------");
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()){
                String msg = sc.nextLine();
                channel.writeAndFlush(msg);
            }
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyChatClient("127.0.0.1",9999).run();
    }
}