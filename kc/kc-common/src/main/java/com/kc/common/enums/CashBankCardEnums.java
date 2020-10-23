package com.kc.common.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum CashBankCardEnums {
    CASH_BANK_CARD_ICBC("ICBC","工商银行"),
    CASH_BANK_CARD_CMB("CMB","招商银行"),
    CASH_BANK_CARD_CCB("CCB","建设银行"),
    CASH_BANK_CARD_ABC("ABC","农业银行"),
    CASH_BANK_CARD_BOC("BOC","中国银行"),
    CASH_BANK_CARD_BOB("BOB","北京银行"),
    CASH_BANK_CARD_COMM("COMM","交通银行"),
    CASH_BANK_CARD_BOD("BOD","东莞银行"),
    CASH_BANK_CARD_GZB("GZB","广州银行"),
    CASH_BANK_CARD_HZB("HZB","杭州银行"),
    CASH_BANK_CARD_BOS("BOS","上海银行"),
    CASH_BANK_CARD_CBHB("CBHB","渤海银行"),
    CASH_BANK_CARD_ECIT("ECITIC","中信银行"),
    CASH_BANK_CARD_CEB("CEB","光大银行"),
    CASH_BANK_CARD_GDB("GDB","广发银行"),
    CASH_BANK_CARD_CMBC("CMBC","民生银行"),
    CASH_BANK_CARD_CIB("CIB","兴业银行"),
    CASH_BANK_CARD_PSBC("PSBC","中国邮政储蓄"),
    CASH_BANK_CARD_SPDB("SPDB","浦发银行"),
    CASH_BANK_CARD_SDB("SDB","深圳发展银行"),
    CASH_BANK_CARD_HXB("HXB","华夏银行"),
    CASH_BANK_CARD_PAB("PAB","平安银行"),
    CASH_BANK_CARD_SRCB("SRCB","上海农商银行");

    private String code;
    private String name;

    public static final Map<String, String> cashBankCardEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(CashBankCardEnums.values()).forEach(e->cashBankCardEnumsMap.put(e.getCode(),e.getName()));
    }
    /**
     * @param code code
     * @param name name
     */
    private CashBankCardEnums(String code, String name) {
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
