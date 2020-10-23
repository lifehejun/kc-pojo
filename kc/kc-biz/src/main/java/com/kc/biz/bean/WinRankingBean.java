package com.kc.biz.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class WinRankingBean implements Serializable {
    private static final long serialVersionUID = 4359709211352400087L;
    private Long id;
    private String userName;
    private String winContent;
    private BigDecimal money;
    private Integer no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWinContent() {
        return winContent;
    }

    public void setWinContent(String winContent) {
        this.winContent = winContent;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
