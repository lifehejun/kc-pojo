package com.kc.biz.service;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.UserBean;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface ITransRecordService {

    int insert(TransRecord transRecord);
    Page<TransRecord> queryByPage(Map<String, Object> params) throws ApiException;
    void rechargeConfirm(Map<String, Object> params) throws ApiException;
    void rechargeReturn(Map<String, Object> params) throws ApiException;
    void manualTransSubmit(UserBean user, TransRecord transRecord) throws ApiException;
    void manualCashTrans(UserBean user, TransRecord transRecord) throws ApiException;
    void manualRechargeTrans(UserBean user, TransRecord transRecord) throws ApiException;
    Page<TransRecord> queryTrans(Map<String, Object> params) throws ApiException;


    int buildTransRecord(String userId, Integer transType, BigDecimal money,Integer addOrSub,Integer status,String remark);



}
