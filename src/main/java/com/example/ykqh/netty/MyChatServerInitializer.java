package com.example.ykqh.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author 杨昆
 * @date 2021/11/23 10:05
 * @describe
 */
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        ch.pipeline().addLast(new HttpServerCodec());
        //以块的方式来写的处理器
        ch.pipeline().addLast(new ChunkedWriteHandler());
        ch.pipeline().addLast(new HttpObjectAggregator(8192));
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
        ch.pipeline().addLast(new MyChannelHandler());
    }
}