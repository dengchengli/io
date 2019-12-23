package com.example.socketdemo;

/**
 * @Author: Dely
 * @Date: 2019/10/17 11:43
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Socket的前置知识学习。
 *
 * InetAddress类：
 *
 * URL类：
 */
public class AddressAndURL {
    public static void main(String[] args) throws IOException {
        URL  url =new URL("http://www.baidu.com");
        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while (line!=null){
            System.out.println(line);
            line = br.readLine();
        }












        /*URL url = new URL("http","www.baidu.com","/index.html?username=dely#file");
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getFile());
        System.out.println(url.getPath());
        System.out.println(url.getQuery());
        System.out.println(url.getRef());*/
    }
}
