package com.kc.common.enums;

/**
 * appid枚举
 */
public enum AppIdEnums {
    APP_ANDROID("1001", "android应用"),
    APP_IOS("1002", "IOS应用"),
    APP_H5("1003", "H5应用")
    ;


    private String code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private AppIdEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
