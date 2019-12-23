package com.example.client;

/**
 * @Author: Dely
 * @Date: 2019/10/18 11:38
 */

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * 启动多个客户端线程来模拟多客户端访问
 */
public class ClientThread implements Callable<String> {
    private Socket socket;

    public ClientThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public String call() throws Exception{
        //从socket中获取输出流
        OutputStream os = null;
        PrintWriter pw = null;  //将输出流包装成打印流
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("我是客户端的登录数据！");
            pw.flush();
            //如果还没关闭输出流，则必须添加这一句，将数据刷新到服务器端，要不服务器无法获取到数据。
            socket.shutdownOutput();
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info = br.readLine();
            while (info != null) {
                System.out.println(info);
                info = br.readLine();
            }
            socket.shutdownInput();
            pw.write("我接收到了服务器的数据！");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
            if (isr != null)
                isr.close();
            if (is != null)
                is.close();
            if (pw != null)
                pw.close();
            if (os != null)
                os.close();
            if (socket != null)
                socket.close();
        }
        return "已成功发送请求！";
    }
}
