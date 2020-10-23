package com.kc.api.utils;

import com.kc.common.util.PropertiesUtil;

public class ApiCommConst {

    private ApiCommConst() {

    }

    public final static String API_AES_KEY = PropertiesUtil.getProperty("api.aes.key");
    public final static String APP_AES_IV = PropertiesUtil.getProperty("api.aes.iv");
}
