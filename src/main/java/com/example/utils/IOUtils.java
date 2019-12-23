package com.example.utils;

import io.netty.buffer.ByteBuf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: Dely
 * @Date: 2019/10/16 11:04
 */

/**
 * 文本：char序列
 * 文件：byte序列
 * 文本文件：将char序列按编码转化为byte序列，然后存储在文件中。
 * 字符流<---->字节流：按一定的编码格式
 */
public class IOUtils {


    /**
     * 复制文件
     * @param src  源文件
     * @param dest  目标文件
     * @throws IOException
     */
    public static void copyFile(File src, File dest) throws IOException {
        if (!src.exists()) {
            throw new IllegalArgumentException("文件" + src + "不存在");
        }
        if (!src.isFile()) {
            throw new IllegalArgumentException(src + "不是文件");
        }
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(dest);
        byte buff[] = new byte[1024 << 2];
        int c;
        while ((c = fis.read(buff)) != -1){
            fos.write(buff,0,c);
        }
    }
}
