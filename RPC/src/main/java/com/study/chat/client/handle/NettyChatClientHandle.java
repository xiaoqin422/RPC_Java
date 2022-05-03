package com.study.chat.client.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 包名: com.study.chat.client
 * 类名: NettyChatClientHandle
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:38
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyChatClientHandle extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }
}