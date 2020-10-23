package com.kc.common.enums;

public enum DictBusTypeEnums {
    VERIFY_CODE("verifyCode", "手机验证码"),
    VOD_TYPE("vodType", "视频类型"),
    VIDEO_LABEL("videoLabel", "视频标签"),
    SYS_HEAD_URL("sysHeadUrl", "系统头像路径url"),
    POST_TOPIC_LABEL("postTopicLabel", "帖子话题标签"),
    CHOOSE_BANK_CARD("chooseBankCard", "可选的银行列表信息"),
    CACHE_MANAGE("cacheManage", "缓存管理"),
    WEB_CONFIG("webConfig", "网站设置"),
    CASH_CONFIG("cashConfig", "提现参数设置"),
    WEB_SWITCH("webSwitch", "网站开关设置")

    ;


    private String code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private DictBusTypeEnums(String code, String name) {
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
