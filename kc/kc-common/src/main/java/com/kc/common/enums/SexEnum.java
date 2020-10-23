package com.kc.common.enums;

public enum SexEnum {
    MAN(0, "男"),
    WOMAN(1, "女");


    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private SexEnum(int code, String name) {
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
