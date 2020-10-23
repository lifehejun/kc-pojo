package com.kc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by mark on 2018/4/21.
 */
public class UserBean implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String userId;
    private String userName;
    private Integer userType; //用户类型：1：普通用户，2：内部测试用户
    private String userPwd; //用户登录密码
    private String cashPwd;
    private BigDecimal bonusRatio; //推荐奖金比例
    private String parentUserId; //代理用户ID
    private String phone;
    private String remark;
    private Integer status;
    private String realName;
    private String address;
    private String qq;
    private String wechat;
    private String headUrl;
    private Integer grade;
    private String agentCode;
    private BigDecimal coreBalance;
    private String regIp;
    private Long createTime;
    //增加字段
    private String vodGradeName;
    //用户类型名称
    private String userTypeDesc;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public BigDecimal getBonusRatio() {
        return bonusRatio;
    }

    public void setBonusRatio(BigDecimal bonusRatio) {
        this.bonusRatio = bonusRatio;
    }

    public String getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(String parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public BigDecimal getCoreBalance() {
        return coreBalance;
    }

    public void setCoreBalance(BigDecimal coreBalance) {
        this.coreBalance = coreBalance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCashPwd() {
        return cashPwd;
    }

    public void setCashPwd(String cashPwd) {
        this.cashPwd = cashPwd;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getVodGradeName() {
        return vodGradeName;
    }

    public void setVodGradeName(String vodGradeName) {
        this.vodGradeName = vodGradeName;
    }

    public Integer getUserType() {
        return userType;
    }

    public String getUserTypeDesc() {
        return userTypeDesc;
    }

    public void setUserTypeDesc(String userTypeDesc) {
        this.userTypeDesc = userTypeDesc;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
