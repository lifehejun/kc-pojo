package com.kc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Coupon implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String couponCode;
    private String couponName;
    private String showName;
    private Integer busType;
    private Integer giveType;
    private Integer couponType;
    private BigDecimal faceValue;
    private BigDecimal subsidyAmount;
    private Integer num;
    private Long sellStartTime;
    private Long sellEndTime;
    private Long validStartTime;
    private Long validEndTime;
    private String ruleDesc;
    private Integer status;
    private Long createTime;

    //
    private String busTypeDesc;
    private String giveTypeDesc;
    private String couponTypeDesc;
    private String statusDesc;

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

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public Integer getGiveType() {
        return giveType;
    }

    public void setGiveType(Integer giveType) {
        this.giveType = giveType;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public BigDecimal getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(BigDecimal faceValue) {
        this.faceValue = faceValue;
    }

    public BigDecimal getSubsidyAmount() {
        return subsidyAmount;
    }

    public void setSubsidyAmount(BigDecimal subsidyAmount) {
        this.subsidyAmount = subsidyAmount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getSellStartTime() {
        return sellStartTime;
    }

    public void setSellStartTime(Long sellStartTime) {
        this.sellStartTime = sellStartTime;
    }

    public Long getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(Long sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public Long getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Long validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Long getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Long validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBusTypeDesc() {
        return busTypeDesc;
    }

    public void setBusTypeDesc(String busTypeDesc) {
        this.busTypeDesc = busTypeDesc;
    }

    public String getGiveTypeDesc() {
        return giveTypeDesc;
    }

    public void setGiveTypeDesc(String giveTypeDesc) {
        this.giveTypeDesc = giveTypeDesc;
    }

    public String getCouponTypeDesc() {
        return couponTypeDesc;
    }

    public void setCouponTypeDesc(String couponTypeDesc) {
        this.couponTypeDesc = couponTypeDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
