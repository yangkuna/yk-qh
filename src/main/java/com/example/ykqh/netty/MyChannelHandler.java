package com.example.ykqh.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 杨昆
 * @date 2021/11/23 10:07
 * @describe 服务端消息处理
 */
@Slf4j
public class MyChannelHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        log.info("与客户端建立连接，通道开启！");
        //添加到channelGroup通道组
        MyChannelHandlerPool.channelGroup.add(ctx.channel());
        sendAllMessage("系统提示：有人上线啦");
        int i = MyChannelHandlerPool.channelGroup.size();
        String num = "num" + i;
        sendAllMessage(num);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("与客户端断开连接，通道关闭！");
        //添加到channelGroup 通道组
        MyChannelHandlerPool.channelGroup.remove(ctx.channel());
        sendAllMessage("系统提示：有人溜走啦");
        int i = MyChannelHandlerPool.channelGroup.size();
        String num = "num" + i;
        sendAllMessage(num);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //首次连接是FullHttpRequest
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            //如果url包含参数，需要处理
            if(uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }
        }else if(msg instanceof TextWebSocketFrame){
            //正常的TEXT消息类型
            TextWebSocketFrame frame=(TextWebSocketFrame)msg;
            if("num".equals(frame.text())){
                int i = MyChannelHandlerPool.channelGroup.size();
                String num = "num" + i;
                sendAllMessage(num);
            }
            else{
                sendAllMessage(frame.text());
            }
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {

    }

    public void sendAllMessage(String message){
        //收到信息后，群发给所有channel
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime.format(formatter);
        if(!message.contains("num")){
            message = dateTime.format(formatter) + " " + message;
        }
        MyChannelHandlerPool.channelGroup.writeAndFlush( new TextWebSocketFrame(message));
    }
}