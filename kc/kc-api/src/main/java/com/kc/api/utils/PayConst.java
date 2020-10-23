package com.kc.api.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AX on 2018/7/11.
 */
public class PayConst {

    public static final Map<String,String> channelCode = new HashMap<String,String>();

    public static final Map<String,String> payPlatform = new HashMap<String,String>();

    static {
        channelCode.put("meituan","美团二维码收款");
        channelCode.put("alipay","支付宝收款");
        channelCode.put("wechat","微信收款");
        channelCode.put("union","银联扫码收款");
    }

    static {
        payPlatform.put("pc","PC端");
        payPlatform.put("mobile","移动端");
    }


}
