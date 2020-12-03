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
    private BigDecimal couponAmount;
    private BigDecimal fullAmount;
    private Integer provideNum; //发放数量
    private Integer receiveNum; //同账号领取数量
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

    private String sellStatus; //销售状态
    private String validStatus; //有效状态

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

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getProvideNum() {
        return provideNum;
    }

    public void setProvideNum(Integer provideNum) {
        this.provideNum = provideNum;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
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

    public BigDecimal getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(BigDecimal fullAmount) {
        this.fullAmount = fullAmount;
    }

    public String getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(String sellStatus) {
        this.sellStatus = sellStatus;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
}
