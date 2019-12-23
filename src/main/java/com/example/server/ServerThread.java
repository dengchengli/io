package com.example.server;

/**
 * @Author: Dely
 * @Date: 2019/10/18 10:46
 */

import sun.misc.Unsafe;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器多线程响应客户端
 */
public class ServerThread implements Callable<String> {
    private Socket socket;
    public static final ThreadLocal<String> local = new ThreadLocal<>();
    private volatile static AtomicInteger count = new AtomicInteger(0);

    public ServerThread(Socket socket) {
        super();
        this.socket = socket;
    }


    @Override
    public String call() throws Exception {
        //获取输入流，读取客户端发送的请求
        InputStream inputStream = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;
        try {
            inputStream = socket.getInputStream();
            reader = new InputStreamReader(inputStream);

            br = new BufferedReader(reader);
            String info;
            while ((info = br.readLine()) != null) {
                System.out.println(info);
            }
            socket.shutdownInput();
            os = socket.getOutputStream();
            pw = new PrintWriter(os);

            pw.write("欢迎您登录！");
            pw.flush();
            //socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } finally {
            count.incrementAndGet();
            if (inputStream != null)
                inputStream.close();
            if (reader != null)
                reader.close();
            if (br != null)
                br.close();
            if (os != null)
                os.close();
            if (pw != null)
                pw.close();
            if (socket != null)
                socket.close();
        }
        System.out.println("响应结束");
        System.out.println(getThreadCounts());
        return "响应结束";
    }

    public static int getThreadCounts() {
        return count.get();
    }
}
