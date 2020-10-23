package com.kc.common.consts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by AX on 2018/3/30.
 */
public class BankConst {

    public static final Map<String,String> OUT_MONEY_BANK_CODE_INFO = new LinkedHashMap<String, String>(); //出款可选择的银行信息

    static {
        OUT_MONEY_BANK_CODE_INFO.put("ICBC","工商银行");
        OUT_MONEY_BANK_CODE_INFO.put("CMB","招商银行");
        OUT_MONEY_BANK_CODE_INFO.put("CCB","建设银行");
        OUT_MONEY_BANK_CODE_INFO.put("ABC","农业银行");
        OUT_MONEY_BANK_CODE_INFO.put("BOC","中国银行");
        OUT_MONEY_BANK_CODE_INFO.put("BOB","北京银行");
        OUT_MONEY_BANK_CODE_INFO.put("COMM","交通银行");
        OUT_MONEY_BANK_CODE_INFO.put("BOD","东莞银行");
        OUT_MONEY_BANK_CODE_INFO.put("GZB","广州银行");
        OUT_MONEY_BANK_CODE_INFO.put("HZB","杭州银行");
        OUT_MONEY_BANK_CODE_INFO.put("BOS","上海银行");
        OUT_MONEY_BANK_CODE_INFO.put("CBHB","渤海银行");
        OUT_MONEY_BANK_CODE_INFO.put("ECITIC","中信银行");
        OUT_MONEY_BANK_CODE_INFO.put("CEB","光大银行");
        OUT_MONEY_BANK_CODE_INFO.put("GDB","广发银行");
        OUT_MONEY_BANK_CODE_INFO.put("CMBC","民生银行");
        OUT_MONEY_BANK_CODE_INFO.put("CIB","兴业银行");
        OUT_MONEY_BANK_CODE_INFO.put("PSBC","中国邮政储蓄");
        OUT_MONEY_BANK_CODE_INFO.put("SPDB","浦发银行");
        OUT_MONEY_BANK_CODE_INFO.put("SDB","深圳发展银行");
        OUT_MONEY_BANK_CODE_INFO.put("HXB","华夏银行");
        OUT_MONEY_BANK_CODE_INFO.put("PAB","平安银行");
        OUT_MONEY_BANK_CODE_INFO.put("SRCB","上海农商银行");
        OUT_MONEY_BANK_CODE_INFO.put("OTHER","其他");
    }



}
