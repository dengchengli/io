package com.example.server;

/**
 * @Author: Dely
 * @Date: 2019/10/18 21:11
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * NioServer服务器端
 */
public class NioServer {
    public static void main(String[] args) {
        try {
            NioServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void start() throws Exception {
        /**
         * 创建多路复用器，用来循环监听客户端就绪事件
         */
        Selector selector = Selector.open();
        /**
         * 创建channel，用来对对数据进行传输。
         */
        ServerSocketChannel channel = ServerSocketChannel.open();
        /**
         * 为channel绑定服务器监听端口
         */
        channel.bind(new InetSocketAddress(8888));
        System.out.println("*******************服务器已启动**************");
        /**
         * 将channel设置为非阻塞io模式
         */
        channel.configureBlocking(false);
        /**
         * 将channel注册到选择器里边。
         */
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            /**
             * 获取就绪事件的数量
             */
            int readys = selector.select();
            if (readys == 0) {
                continue;
            }

            /**
             * 获取就绪事件的集合
             */
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            /**
             * 通过迭代器来遍历就绪事件
             */
            Iterator it = selectionKeys.iterator();

            while (it.hasNext()) {
                /**
                 * 获取集合中的第一个元素
                 */
                SelectionKey selectionKey = (SelectionKey) it.next();
                /**
                 * 移除集合中的当前正在处理的就绪事件
                 */
                it.remove();
                /**
                 * 接入事件
                 */
                if (selectionKey.isAcceptable()) {
                    acceptHandler(channel, selector);
                }
                /**
                 * 可读事件
                 */
                if (selectionKey.isReadable()){
                    readHandler(selectionKey,  selector);
                }
            }

        }
    }

    /**
     * 接入事件处理器
     * @param serverSocketChannel
     * @param selector
     * @throws IOException
     */
    private static void acceptHandler(ServerSocketChannel serverSocketChannel,Selector selector) throws IOException {
        /**
         * 监听就绪的SocketChannel
         */
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        socketChannel.write(Charset.forName("gbk").encode("您已连接上服务器！"));
    }

    private  static void readHandler(SelectionKey selectionKey,  Selector  selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer  byteBuffer =  ByteBuffer.allocate(1024);
        String request = "";
        while (socketChannel.read(byteBuffer) >0){
            byteBuffer.flip();
            request+= Charset.forName("gbk").decode(byteBuffer);
        }

        socketChannel.register(selector,SelectionKey.OP_READ);
        if (request.length()>0){
            System.out.println("客户端传过来的数据为："+request);
        }
    }
}
