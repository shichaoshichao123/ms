package com.sc.ms.goodBye996.functionInterface;

/**
 * @author :  sc
 * @version :  1.0
 * @description :  函数接口
 * @createDate :  2020/3/23$ 11:42 AM$
 * @updateUser :  sc
 * @updateDate :  2020/3/23$ 11:42 AM$
 * @updateRemark :  修改内容 /Users/mac/study/ms/src/main/java/com/sc/ms/goodBye996functionInterface
 */
@FunctionalInterface
public interface FileConsumer {

    /**
     * 函数式接口抽象方法
     *
     * @param fileContent
     */
    void doHandle(String fileContent);
}
