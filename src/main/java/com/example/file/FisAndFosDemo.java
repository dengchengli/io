package com.example.file;

/**
 * @Author: Dely
 * @Date: 2019/10/16 11:10
 */

import com.example.utils.IOUtils;

import java.io.File;
import java.io.IOException;

/**
 * 测试类
 */
public class FisAndFosDemo {

    public static void main(String[] args) {
        File src = new File("F:\\file\\fis.txt");
        File dest = new File("F:\\file\\fos.txt");
        try {
            IOUtils.copyFile(src,dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
