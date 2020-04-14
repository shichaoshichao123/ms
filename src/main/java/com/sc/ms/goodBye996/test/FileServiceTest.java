package com.sc.ms.goodBye996.test;

import com.sc.ms.goodBye996.service.FileService;

import java.io.IOException;

/**
 * @author :  sc
 * @version :  1.0
 * @description :  java类作用描述
 * @createDate :  2020/3/23$ 11:55 AM$
 * @updateUser :  sc
 * @updateDate :  2020/3/23$ 11:55 AM$
 * @updateRemark :  修改内容
 */
public class FileServiceTest {

    public static void main(String[] args) throws IOException {
        FileService fileService = new FileService();
        fileService.fileHandle("/Users/mac/study/ms/src/main/java/com/sc/ms/goodBye996/test/file.text", System.out::println);
    }

}
