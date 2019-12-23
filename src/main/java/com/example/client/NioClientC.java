package com.example.client;

import java.io.IOException;

/**
 * @Author: Dely
 * @Date: 2019/10/19 13:49
 */
public class NioClientC {
    public static void main(String[] args) {
        try {
            NioClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
