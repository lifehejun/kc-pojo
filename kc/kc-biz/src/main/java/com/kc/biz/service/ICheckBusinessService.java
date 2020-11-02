package com.kc.biz.service;

import com.kc.biz.bean.UserBean;
import com.kc.common.exception.ApiException;

import java.math.BigDecimal;

public interface ICheckBusinessService {
    void checkManualCash(UserBean user, BigDecimal cashMoney) throws ApiException;; //校验人工提现
    void checkIpRegSize(String regIp) throws ApiException;; //校验一个ip下可以注册会员个数
    void checkForbidWord(String postTitle) throws ApiException;//发布帖子时校验违禁词

}
