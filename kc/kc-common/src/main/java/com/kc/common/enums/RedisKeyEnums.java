package com.kc.common.enums;

public enum RedisKeyEnums {

    /**
     * redis中的key
     */
    KC("kc:", "KC"),
    KC_DICT("kc:dict:", "KC_DICT"),
    VOD_LABEL_CODE("kc:dict:videoLabel:", "视频标签"),
    VOD_LABEL_CODE_LIST("kc:dict:videoLabelCodeList:", "视频(标签)集合"),
    SYS_HEAD_URL("kc:dict:sysHeadUrl:", "系统头像路径url"),
    TOPIC_CODE("kc:dict:topicCode:", "帖子话题(标签)"),
    TOPIC_CODE_LIST("kc:dict:topicCodeList:", "帖子话题(标签)集合"),
    FORBID_WORD("kc:dict:forbidWord:","违禁词"),
    CHOOSE_BANK_CARD("kc:dict:chooseBankCard:", "可选的银行列表信息"),
    CACHE_MANAGE("kc:dict:cacheManage:", "缓存管理"),
    WEB_CONFIG("kc:dict:webConfig:", "网站设置"),
    CASH_CONFIG("kc:dict:cashConfig:", "提现参数设置"),
    WEB_SWITCH("kc:dict:webSwitch:", "网站开关设置"),
    TENCENT_COULD_COS("kc:dict:tencentCouldCos:", "腾讯云COS对象存储配置"),



    VERIFY_CODE("kc:check:verifyCode:", "手机验证码"),
    PASSWORD_ERROR_TIMES("kc:check:passwordErrorTimes:", "密码输入错误次数"),
    STATISTICS_REG("kc:statistics:reg:", "注册数"),
    STATISTICS_RECHARGE("kc:statistics:recharge:", "充值数"),
    STATISTICS_POST_NUM("kc:statistics:postNum:", "发帖数"),
    AUTH_USER_TOKEN("kc:auth:user:", "用户token"),
    LOCK_USER_REGISTER("kc:lock:userRegister:", "用户注册锁"),
    LOCK_USER_RECEIVE_COUPON("kc:lock:userReceiveCoupon:", "用户领取优惠券锁"),
    ;


    private String code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private RedisKeyEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
