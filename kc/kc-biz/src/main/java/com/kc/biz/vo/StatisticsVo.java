package com.kc.biz.vo;

import java.math.BigDecimal;

public class StatisticsVo {
    private Integer todayReg;
    private BigDecimal todayRecharge;



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
}
