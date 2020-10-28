package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CouponBusTypeEnums {
    //业务类型：801:充值 802：开通视频会员
    COUPON_BUS_TYPE_801(801, "充值"),
    COUPON_BUS_TYPE_802(802, "开通视频会员");

    public static final Map<Integer, String> couponBusTypeEnumsMap = new HashMap<>();

    static {
        Arrays.stream(CouponBusTypeEnums.values()).forEach(e->couponBusTypeEnumsMap.put(e.getCode(),e.getName()));
    }
    private int code;
    private String name;

    /**
     * @param code code
     * @param name name
     */
    private CouponBusTypeEnums(int code, String name) {
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
    public static String getName(int code){
        for(CouponBusTypeEnums couponBusTypeEnums : values()){
            if(couponBusTypeEnums.getCode() == code){
                return couponBusTypeEnums.getName();
            }
        }
        return StringUtils.EMPTY;
    }
}
