package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CouponTypeEnums {
    //优惠券类型：1:满减券，2，立减券
    COUPON_TYPE_1(1, "满减券"),
    COUPON_TYPE_2(2, "立减券");

    public static final Map<Integer, String> couponTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(CouponTypeEnums.values()).forEach(e->couponTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private CouponTypeEnums(int code, String name) {
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
        for(CouponTypeEnums couponTypeEnums : values()){
            if(couponTypeEnums.getCode() == code.intValue()){
                return couponTypeEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
