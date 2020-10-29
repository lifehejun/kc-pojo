package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public enum TransTypeEnums {
    TRANS_TYPE_100(100, "充值",1),
    TRANS_TYPE_101(101, "提现",0),
    TRANS_TYPE_102(102, "开通视频会员",0),
    TRANS_TYPE_103(103, "推荐好友收益",1),
    TRANS_TYPE_104(104, "打赏",0),
    TRANS_TYPE_105(105, "被打赏",1),
    TRANS_TYPE_106(106, "平台优惠",1),
    ;



    public static final Map<Integer, String> transTypeEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(TransTypeEnums.values()).forEach(e->transTypeEnumsMap.put(e.getCode(),e.getName()));
    }

    private Integer code;
    private String name;
    private Integer addOrSub;


    /**
     * @param code code
     * @param name name
     */
    private TransTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private TransTypeEnums(Integer code, String name,Integer addOrSub) {
        this.code = code;
        this.name = name;
        this.addOrSub = addOrSub;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAddOrSub() {
        return addOrSub;
    }

    public void setAddOrSub(Integer addOrSub) {
        this.addOrSub = addOrSub;
    }

    /**
     * 根据状态码值获取name值
     * @param code
     * @return
     */
    public static String getName(Integer code){
        if(null == code){
            return StringUtils.EMPTY;
        }
        for(TransTypeEnums statusEnum : values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum.getName();
            }
        }
        return StringUtils.EMPTY;
    }
    /**
     * 根据状态码值获取addOrSub值
     * @param code
     * @return
     */
    public static Integer getAddOrSub(Integer code){
        for(TransTypeEnums statusEnum : values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum.getAddOrSub();
            }
        }
        return null;
    }
}
