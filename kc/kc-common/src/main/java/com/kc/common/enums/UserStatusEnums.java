package com.kc.common.enums;

public enum UserStatusEnums {

    USER_STATUS_0(0, "冻结"),
    USER_STATUS_1(1, "正常");


    private Integer status;
    private String name;

    /**
     * @param status
     * @param name
     */
    private UserStatusEnums(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
