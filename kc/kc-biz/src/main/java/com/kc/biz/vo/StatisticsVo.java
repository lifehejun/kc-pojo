package com.kc.biz.vo;

import java.math.BigDecimal;

public class StatisticsVo {
    private Integer todayReg; //今日注册数
    private BigDecimal todayRecharge; //今日充值数
    private Integer todayPostNum; //今日发帖数




    public Integer getTodayReg() {
        return todayReg;
    }

    public void setTodayReg(Integer todayReg) {
        this.todayReg = todayReg;
    }

    public BigDecimal getTodayRecharge() {
        return todayRecharge;
    }

    public void setTodayRecharge(BigDecimal todayRecharge) {
        this.todayRecharge = todayRecharge;
    }

    public Integer getTodayPostNum() {
        return todayPostNum;
    }

    public void setTodayPostNum(Integer todayPostNum) {
        this.todayPostNum = todayPostNum;
    }
}
