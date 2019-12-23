package com.example.client;

/**
 * @Author: Dely
 * @Date: 2019/10/18 21:11
 */

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * NioClient客户端
 */
public class NioClient {
    public static void main(String[] args) {

    }

    public static void start() throws IOException {
        /**
         * 创建客户端的channel连接
         */
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        /**
         * 打开selector多路复用器
         */
        Selector selector = Selector.open();
        /**
         * 设置非阻塞模式
         */
        channel.configureBlocking(false);
        /**
         * 注册监听channel的事件类型到选择器
         */
        channel.register(selector, SelectionKey.OP_READ);
        new Thread(new NioCLientHandler(selector)).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            if (!StringUtils.isEmpty(message)) {
                //发送数据到服务器
                channel.write(Charset.forName("gbk").encode(message));

            }
        }
    }
}
