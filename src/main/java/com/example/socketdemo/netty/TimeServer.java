package com.example.socketdemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: Dely
 * @Date: 2019/11/4 13:27
 */

public class TimeServer {
    private int port = 9001;

    public void run() throws Exception {
        /**
         *
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /**
         *
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();
        } finally {
            System.out.println("服务器执行完了");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
    public static void main(String[] args) {
        try {
            new TimeServer().run();
        } catch (Exception e) {
            System.out.println("服务器出现异常：***********************"+e.getMessage());
        }
    }
}
