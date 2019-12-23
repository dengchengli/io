package com.example.server;

/**
 * @Author: Dely
 * @Date: 2019/10/11 23:30
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 基于tcp协议的socket服务器端
 * 该方式为bio方式（阻塞io）
 * <p>
 * 步骤：
 * 1.创建服务器端的ServerSocket
 * 2.死循环使用accept方法来监听服务器端的端口，并且通过线程池来进行对启动多线程来处理客户端请求。
 * 3.通过socket对象来对数据进行处理
 * 4.关闭资源。
 */
public class SocketServer {
    public static void main(String[] args) {
        try {
            //创建服务器端的socket
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("服务器已启动*************************");
            //创建线程池
            ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
            CompletionService<String> service = new ExecutorCompletionService<>(pool);
            while (true) {
                //监听端口，处于阻塞状态
                Socket socket = serverSocket.accept();
                ServerThread thread = new ServerThread(socket);
                service.submit(thread);
                //调动了future的方法才产生阻塞
                if (service.take().get().equals("响应结束")) {
                    int count = ServerThread.getThreadCounts();
                    System.out.println("线程池存活线程：" + pool.getActiveCount() + "队列中线程数" +
                            pool.getQueue().size() + "线程池中的线程数" + pool.getPoolSize());
                }

            }
        } catch (Exception e) {
            System.out.println("服务器异常！");
            e.printStackTrace();
        }
    }

}
