package com.kc.common.enums;

public enum AddOrSubEnums {

    SUB(-1, "负数"),
    ZERO(0, "零"),
    ADD(1, "正数");


    private Integer code;
    private String name;

    /**
     * @param code
     * @param name
     */
    private AddOrSubEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
