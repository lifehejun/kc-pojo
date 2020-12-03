package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CouponOrderStatusEnums {
    COUPON_ORDER_STATUS_0(0, "未使用"),
    COUPON_ORDER_STATUS_1(1, "已使用"),
    COUPON_ORDER_STATUS_2(2, "已过期");

    public static final Map<Integer, String> couponBusTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(CouponOrderStatusEnums.values()).forEach(e->couponBusTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private CouponOrderStatusEnums(int code, String name) {
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

    /**
     * 根据code获取name值
     * @param code
     * @return
     */
    public static String getName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(CouponOrderStatusEnums couponOrderStatusEnums : values()){
            if(couponOrderStatusEnums.getCode() == code.intValue()){
                return couponOrderStatusEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
