package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CouponGiveTypeEnums {
    //发放方式：1:活动赠送，2，直接领取，3 售卖
    COUPON_GIVE_TYPE_1(1, "活动赠送"),
    COUPON_GIVE_TYPE_2(2, "直接领取"),
    COUPON_GIVE_TYPE_3(3, "售卖");

    public static final Map<Integer, String> couponGiveTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(CouponGiveTypeEnums.values()).forEach(e->couponGiveTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private CouponGiveTypeEnums(int code, String name) {
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
        for(CouponGiveTypeEnums couponGiveTypeEnums : values()){
            if(couponGiveTypeEnums.getCode() == code.intValue()){
                return couponGiveTypeEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
