package com.sc.ms.goodBye996.service;

import com.sc.ms.goodBye996.functionInterface.FileConsumer;

import java.io.*;

/**
 * @author :  sc
 * @version :  1.0
 * @description :  java类作用描述
 * @createDate :  2020/3/23$ 11:44 AM$
 * @updateUser :  sc
 * @updateDate :  2020/3/23$ 11:44 AM$
 * @updateRemark :  修改内容
 */
public class FileService {

    /**
     * 根据函数式接口是想不同的文件操作
     *
     * @param path
     * @param fileConsumer
     */
    public void fileHandle(String path, FileConsumer fileConsumer) throws IOException {
        //获取输入流读取文件内容
        FileInputStream fileInputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }

        //调用函数接口
        fileConsumer.doHandle(stringBuilder.toString());
    }
}
