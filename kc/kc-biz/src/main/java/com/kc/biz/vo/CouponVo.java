package com.kc.biz.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CouponVo implements Serializable {

    private static final long serialVersionUID = 4359709211352411087L;
    private Long id;
    private String couponCode; //优惠券代码
    private String showName; //前端显示名称
    private String couponName;//名称
    private String busTypeDesc; //业务类型
    private BigDecimal couponAmount; //优惠金额，面值
    private String ruleDesc; //规则描述
    private Integer provideNum;
    private BigDecimal buyRatio; //已领取比例
    private Integer status;//状态
    private Integer busType;
    private String sellStartTimeStr;
    private String sellEndTimeStr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getBusTypeDesc() {
        return busTypeDesc;
    }

    public void setBusTypeDesc(String busTypeDesc) {
        this.busTypeDesc = busTypeDesc;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Integer getProvideNum() {
        return provideNum;
    }

    public void setProvideNum(Integer provideNum) {
        this.provideNum = provideNum;
    }

    public BigDecimal getBuyRatio() {
        return buyRatio;
    }

    public void setBuyRatio(BigDecimal buyRatio) {
        this.buyRatio = buyRatio;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public String getSellStartTimeStr() {
        return sellStartTimeStr;
    }

    public void setSellStartTimeStr(String sellStartTimeStr) {
        this.sellStartTimeStr = sellStartTimeStr;
    }

    public String getSellEndTimeStr() {
        return sellEndTimeStr;
    }

    public void setSellEndTimeStr(String sellEndTimeStr) {
        this.sellEndTimeStr = sellEndTimeStr;
    }


}
