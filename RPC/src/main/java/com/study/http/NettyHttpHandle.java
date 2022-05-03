package com.study.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 包名: com.study.http
 * 类名: NettyHttpHandle
 * 创建用户: 25789
 * 创建日期: 2022年05月03日 14:21
 * 项目名: RPC_Java
 *
 * @author: 秦笑笑
 **/
public class NettyHttpHandle extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取就绪事件
     * @param channelHandlerContext 通道上下文
     * @param msg http消息体
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject msg) throws Exception {
        // 1.判断请求是不是请求体
        if (msg instanceof HttpRequest){
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            System.out.println("浏览器请求路径：" + request.uri());
            if ("/favicon.ico".equals(request.uri())){
                System.out.println("图标不响应");
                return;
            }
            //2.给浏览器进行响应
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hellp! 我是Netty服务器", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new
                    DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,byteBuf);
            //3.设置http响应头
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/html;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            channelHandlerContext.writeAndFlush(response);
        }
    }
}