package com.example.client;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @Author: Dely
 * @Date: 2019/10/11 23:47
 */

/**
 * 步骤：
 * 1.创建与服务端交互的socket
 * 2.通过socket来和服务器端进行数据交互。
 * 3.关闭资源。
 */
public class SocketClient {
    public static void main(String[] args) {
        try {
            //创建socket
            ThreadPoolExecutor pool = new ThreadPoolExecutor(20, 40, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
            CompletionService<String> service = new ExecutorCompletionService<>(pool);
            for (int i = 0; i < 1000; i++) {
                Socket socket = new Socket("localhost", 9000);
                ClientThread clientThread = new ClientThread(socket);
                service.submit(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
