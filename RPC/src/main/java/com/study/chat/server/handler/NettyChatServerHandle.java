package com.study.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名: com.study.chat.server.handler
 * 类名: NettyChatServerHandle
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:22
 * 项目名: RPC_Java
 * 聊天室业务处理类
 * @author: 秦笑笑
 **/
public class NettyChatServerHandle extends SimpleChannelInboundHandler<String> {
    public static List<Channel> channelList = new ArrayList<>();
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
        for (Channel client : channelList) {
            if (client != channel){
                client.writeAndFlush("[" +
                        channel.remoteAddress().toString().substring(1) + "]说:" +s);
            }
        }
    }

    /**
     * 通道就绪事件
     * @param ctx 通道上下文
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.add(channel);
        System.out.println("[Server]:" + channel.remoteAddress().toString().substring(1) + "在线.");
    }

    /**
     * 通道未就绪--channel下线
     * @param ctx 通道上下文
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println("[Server]:" +
                channel.remoteAddress().toString().substring(1) + "下线.");
    }

    /**
     * 通道异常处理
     * @param ctx 通道上下文环境
     * @param cause 异常原因
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println("[Server]:" + channel.remoteAddress().toString().substring(1) + "异常.");
    }
}