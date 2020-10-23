package com.kc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class VipGrade implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String vipCode;
    private Integer grade;
    private BigDecimal oldMoney;
    private BigDecimal money;
    private String vipName;
    private String vipDesc;
    private String tips;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVipCode() {
        return vipCode;
    }

    public void setVipCode(String vipCode) {
        this.vipCode = vipCode;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BigDecimal getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(BigDecimal oldMoney) {
        this.oldMoney = oldMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipDesc() {
        return vipDesc;
    }

    public void setVipDesc(String vipDesc) {
        this.vipDesc = vipDesc;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
