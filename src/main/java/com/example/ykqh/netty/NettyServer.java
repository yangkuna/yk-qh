package com.example.ykqh.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author 杨昆
 * @date 2021/11/23 10:00
 * @describe 服务端处理
 */
@Slf4j
@Configuration
public class NettyServer {

    @Value("${netty.server.port}")
    public Integer port;

    private void startServer(){
        //服务端需要2个线程组  boss处理客户端连接  work进行客服端连接之后的处理
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            // ServerBootstrap 是一个启动NIO服务的辅助启动类 你可以在这个服务中直接使用Channel
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .localAddress(port)
                    .childHandler(new MyChatServerInitializer());
            //绑定端口  开启事件驱动
            Channel channel = bootstrap.bind().sync().channel();
            channel.closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    /**
     * 当bean创建完成的时候，会后置执行@PostConstruct修饰的方法
     */
    @PostConstruct()
    public void init(){
        //需要开启一个新的线程来执行netty server 服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();
    }

}