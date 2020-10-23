package com.kc.common.enums;

/**
 * 统计类型枚举
 */
public enum CountTypeEnum {
    REG(100, "注册统计"),
    RECHARGE(101, "充值统计");


    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private CountTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
