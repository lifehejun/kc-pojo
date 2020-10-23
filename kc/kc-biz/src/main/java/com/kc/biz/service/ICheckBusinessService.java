package com.kc.biz.service;

import com.kc.biz.bean.UserBean;

import java.math.BigDecimal;

public interface ICheckBusinessService {
    void checkManualCash(UserBean user, BigDecimal cashMoney); //校验人工提现
    void checkIpRegSize(String regIp); //校验一个ip下可以注册会员个数

}
