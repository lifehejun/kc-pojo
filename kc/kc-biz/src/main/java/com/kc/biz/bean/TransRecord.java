package com.kc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransRecord implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String userId;
    private String transNo;
    private Integer transType;
    private BigDecimal money;
    private Integer addOrSub;
    private BigDecimal beforeMoney;
    private BigDecimal afterMoney;
    private Integer beforeGoldCoin;
    private Integer afterGoldCoin;
    private Integer status;
    private String subServiceId;
    private Integer goldCoin;
    private String remark;
    private Long transTime;
    private Long createTime;

    //扩展字段vo
    private String userName;
    private String phone;
    private String transTypeDesc;
    private String transValue; //交易值：包括金币，金额值，定义为字符串类型

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getAddOrSub() {
        return addOrSub;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(String subServiceId) {
        this.subServiceId = subServiceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTransTime() {
        return transTime;
    }

    public void setTransTime(Long transTime) {
        this.transTime = transTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTransTypeDesc() {
        return transTypeDesc;
    }

    public void setTransTypeDesc(String transTypeDesc) {
        this.transTypeDesc = transTypeDesc;
    }

    public Integer getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(Integer goldCoin) {
        this.goldCoin = goldCoin;
    }

    public void setAddOrSub(Integer addOrSub) {
        this.addOrSub = addOrSub;
    }

    public BigDecimal getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(BigDecimal beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public Integer getBeforeGoldCoin() {
        return beforeGoldCoin;
    }

    public void setBeforeGoldCoin(Integer beforeGoldCoin) {
        this.beforeGoldCoin = beforeGoldCoin;
    }

    public Integer getAfterGoldCoin() {
        return afterGoldCoin;
    }

    public void setAfterGoldCoin(Integer afterGoldCoin) {
        this.afterGoldCoin = afterGoldCoin;
    }

    public String getTransValue() {
        return transValue;
    }

    public void setTransValue(String transValue) {
        this.transValue = transValue;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
