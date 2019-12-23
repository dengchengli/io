package com.example.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: Dely
 * @Date: 2019/10/19 14:54
 */
public class NioCLientHandler implements Runnable {
    private Selector selector;

    public NioCLientHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        /**
         *
         */
        while (true) {
            try {
                int readyChannel = selector.select();
                if (readyChannel == 0) {
                    continue;
                }

                /**
                 * SelectionKey代表已经注册到selector的channel
                 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey>  it = selectionKeys.iterator();
                while (it.hasNext()) {
                    SelectionKey selectionKey = it.next();
                    it.remove();
                    if (selectionKey.isReadable()) {
                        readHandler(selectionKey, selector);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  static void readHandler(SelectionKey selectionKey,  Selector  selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer =  ByteBuffer.allocate(1024);
        String request = "";
        while (socketChannel.read(byteBuffer) >0){
            byteBuffer.flip();
            request+= Charset.forName("gbk").decode(byteBuffer);
        }

        socketChannel.register(selector,SelectionKey.OP_READ);
        if (request.length()>0){
            System.out.println("客户端收到的数据为："+request);
        }
    }
}
