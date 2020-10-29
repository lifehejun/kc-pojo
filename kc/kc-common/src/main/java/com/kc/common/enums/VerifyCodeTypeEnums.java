package com.kc.common.enums;

public enum VerifyCodeTypeEnums {
    VERIFY_CODE_TYPE_REG("reg", "注册手机验证码"),
    VERIFY_CODE_TYPE_LOGIN("login", "登陆手机验证码");


    private String code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private VerifyCodeTypeEnums(String code, String name) {
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
