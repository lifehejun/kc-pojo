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
    private BigDecimal beforeBalance;
    private BigDecimal afterBalance;
    private Integer status;
    private String remark;
    private Long transTime;
    private Long createTime;

    //扩展字段vo
    private String userName;
    private String phone;
    private String transTypeDesc;

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

    public void setAddOrSub(Integer addOrSub) {
        this.addOrSub = addOrSub;
    }

    public BigDecimal getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(BigDecimal beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
