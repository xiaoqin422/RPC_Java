package com.study.chat.config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * 包名: com.study.chat.config
 * 类名: MessageEncoder
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 13:31
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class MessageCoder extends MessageToMessageCodec {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        String msg = (String) o;
        list.add(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {
        ByteBuf byteBuf = (ByteBuf) o;
        list.add(byteBuf.toString(CharsetUtil.UTF_8));
    }
}