package com.kc.common.enums;

import org.apache.commons.lang.StringUtils;

import java.util.*;

public enum TransTypeEnums {
    TRANS_TYPE_100(100, "现金充值",1),
    TRANS_TYPE_101(101, "提现",-1),
    TRANS_TYPE_102(102, "推荐好友收益",1),

    TRANS_TYPE_103(103, "购买视频会员卡",0),

    TRANS_TYPE_200(200, "金币充值",1),
    TRANS_TYPE_201(201, "金币兑换现金",-1),
    TRANS_TYPE_202(202, "金币打赏",-1),
    TRANS_TYPE_203(203, "金币被打赏",1),
    TRANS_TYPE_204(204, "金币解锁",-1),

    ;



    public static final Map<Integer, String> transTypeEnumsMap = new LinkedHashMap<>();
    public static final Map<Integer, String> moneyTransTypeEnumsMap = new LinkedHashMap<>();
    public static final Map<Integer, String> goldCoinTransTypeEnumsMap = new LinkedHashMap<>();

    static {
        Arrays.stream(TransTypeEnums.values()).forEach(e->transTypeEnumsMap.put(e.getCode(),e.getName()));
        //现金类交易map集合
        Arrays.stream(TransTypeEnums.values()).filter(
                transTypeEnums->transTypeEnums.getCode() == TransTypeEnums.TRANS_TYPE_100.getCode() ||
                        transTypeEnums.getCode() == TransTypeEnums.TRANS_TYPE_101.getCode()||
                        transTypeEnums.getCode() == TransTypeEnums.TRANS_TYPE_102.getCode()).forEach(e->moneyTransTypeEnumsMap.put(e.getCode(),e.getName()));

        //金币类交易map集合
        Arrays.stream(TransTypeEnums.values()).filter(
                transTypeEnums->transTypeEnums.getCode() == TransTypeEnums.TRANS_TYPE_200.getCode() ||
                        transTypeEnums.getCode() == TransTypeEnums.TRANS_TYPE_201.getCode()).forEach(e->goldCoinTransTypeEnumsMap.put(e.getCode(),e.getName()));
    }

    //现金类交易code集合
    public static final List<Integer> moneyTransTypeCodeList = Arrays.asList(new Integer[]{TRANS_TYPE_100.getCode(),TRANS_TYPE_101.getCode(),TRANS_TYPE_102.getCode(),TRANS_TYPE_103.getCode()});

    //金币交易code集合
    public static final List<Integer> goldCoinTransTypeCodeList = Arrays.asList(new Integer[]{TRANS_TYPE_200.getCode(),TRANS_TYPE_201.getCode(),TRANS_TYPE_202.getCode(),TRANS_TYPE_203.getCode(),TRANS_TYPE_204.getCode()});


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
