package com.kc.biz.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class CouponOrderVo implements Serializable {

    private static final long serialVersionUID = 4359709211352411087L;
    private Long id;
    private String couponCode; //优惠券代码
    private String receiveTime; //领取时间
    private String showName; //前端显示名称
    private String couponName;//名称
    private Integer busType; //业务类型编号
    private String busTypeDesc; //业务类型描述
    private BigDecimal couponAmount; //优惠金额，面值
    private String ruleDesc; //规则描述
    private Integer orderStatus;//状态
    private Long validStartTime; //有效开始时间
    private Long validEndTime;//有效结束时间

    private String validTimeDesc; //有效时间描述
    private String orderStatusDesc;//状态状态描述

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

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
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

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getValidTimeDesc() {
        return validTimeDesc;
    }

    public void setValidTimeDesc(String validTimeDesc) {
        this.validTimeDesc = validTimeDesc;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }
}
