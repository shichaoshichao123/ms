package com.sc.ms.juc;

/**
 * @author: shichao
 * @date: 2019/4/25
 * @description: 国家的枚举
 */
public enum CountyEnum {
    /**
     * 各国枚举
     */
    One(1, "齐"),
    Two(2, "楚"),
    Three(3, "燕"),
    Four(4, "韩"),
    Five(5, "赵"),
    Six(6, "魏");
    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    CountyEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CountyEnum forEachByIndex(int index) {
        CountyEnum[] enums = CountyEnum.values();
        for (CountyEnum item : enums) {
            if (item.getCode() == index) {
                return item;
            }
        }
        return null;
    }
}
