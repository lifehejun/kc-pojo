package com.kc.biz.service.impl;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.UserBean;
import com.kc.biz.mapper.TransRecordMapper;
import com.kc.biz.mapper.UserMapper;
import com.kc.biz.service.ICheckBusinessService;
import com.kc.biz.service.ITransRecordService;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.AddOrSubEnums;
import com.kc.common.enums.TransTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.DateTools;
import com.kc.common.util.GenerationUtil;
import com.kc.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class TransRecordServiceImpl implements ITransRecordService {

    @Autowired
    private TransRecordMapper transRecordMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ICheckBusinessService checkBusinessService;


    @Override
    public int insert(TransRecord transRecord) {
        return transRecordMapper.insert(transRecord);
    }

    @Override
    public Page<TransRecord> queryByPage(Map<String, Object> params) throws ApiException {
        int total = transRecordMapper.getTotal(params);
        if (total > 0) {
            List<TransRecord> list = transRecordMapper.queryByPage(params);
            list.forEach(transRecord -> {
                transRecord.setTransTypeDesc(TransTypeEnums.getName(transRecord.getTransType()));
            });
            return new Page<TransRecord>(list, total);
        }else{
            return new Page<TransRecord>(null, 0);
        }
    }

    @Override
    public void rechargeConfirm(Map<String, Object> params) throws ApiException {

        String userId = String.valueOf(params.get("userId"));
        String transNo = String.valueOf(params.get("transNo"));

        //交易信息
        TransRecord transRecord = transRecordMapper.queryByTransNo(transNo);

        if(CommConst.TRANS_STATUS_0.equals(transRecord.getStatus())){
            //用户信息
            UserBean userBean = userMapper.queryByUserId(userId);
            //充值前的余额
            BigDecimal beforeMoney = userBean.getCoreBalance();
            //充值后的余额
            BigDecimal afterMoney = beforeMoney.add(transRecord.getMoney());

            //更新交易状态
            transRecord.setStatus(CommConst.TRANS_STATUS_1); //交易成功
            transRecord.setBeforeMoney(beforeMoney);
            transRecord.setAfterMoney(afterMoney);
            transRecord.setTransTime(DateTools.getUnixTimestampTime(new Date()));
            transRecord.setRemark("充值:人工确认");
            transRecordMapper.updateById(transRecord);

            //更新用户核心余额
            userBean.setCoreBalance(afterMoney);
            userMapper.updateByUserId(userBean);
        }else{
            throw new ApiException(BusinessCode.TRANS_RESP_4001.getCode());
        }


    }

    @Override
    public void rechargeReturn(Map<String, Object> params) throws ApiException {
        String transNo = String.valueOf(params.get("transNo"));
        String userId = String.valueOf(params.get("userId"));
        //用户信息
        UserBean userBean = userMapper.queryByUserId(userId);
        BigDecimal coreBalance = userBean.getCoreBalance();
        //交易信息
        TransRecord transRecord = transRecordMapper.queryByTransNo(transNo);

        if(CommConst.TRANS_STATUS_0.equals(transRecord.getStatus())){
            transRecord.setBeforeMoney(coreBalance);
            transRecord.setAfterMoney(coreBalance);
            transRecord.setStatus(CommConst.TRANS_STATUS_2); //交易失败
            transRecord.setRemark("充值:人工撤销");
            transRecordMapper.updateById(transRecord);
        }else{
            throw new ApiException(BusinessCode.TRANS_RESP_4001.getCode());
        }


    }

    @Override
    public void manualTransSubmit(UserBean user, TransRecord transRecord) throws ApiException {
        Integer transType = transRecord.getTransType();
        if(null == transType){
            throw new ApiException(BusinessCode.TRANS_RESP_4003.getCode());
        }
        switch(transType){
            case 100: //充值
                this.manualRechargeTrans(user,transRecord);
                break;
            case 101: //提现
                this.manualCashTrans(user,transRecord);
                break;
            case 102:
                //;
                break;
            case 103:
                //;
                break;
            case 200:
                this.manualGoldCoinRechargeTrans(user,transRecord);
                break;
            case 105:
                //;
                break;
            case 106:
                //;
                break;
            default: break;
        }

    }

    @Override
    public void manualRechargeTrans(UserBean user, TransRecord transRecord) throws ApiException {
        String userId = user.getUserId();
        Integer transType = transRecord.getTransType();
        String transValue = transRecord.getTransValue();
        BigDecimal money = new BigDecimal(transValue).setScale(2,BigDecimal.ROUND_HALF_UP);
        if(money.compareTo(BigDecimal.ZERO)<=0){
            throw new ApiException(BusinessCode.TRANS_RESP_4002.getCode());
        }

        Integer addOrSub = TransTypeEnums.getAddOrSub(transType);
        String transTypeDesc = TransTypeEnums.getName(transType);
        //构建交易记录
        this.buildTransRecord(userId,transType,money,null,addOrSub,CommConst.TRANS_STATUS_1,null,transTypeDesc);
    }

    @Override
    public void manualGoldCoinRechargeTrans(UserBean user, TransRecord transRecord) throws ApiException {
        String userId = user.getUserId();
        Integer transType = transRecord.getTransType();
        String transValue = transRecord.getTransValue();
        if(!StringUtil.isInt(transValue)){
            throw new ApiException(BusinessCode.TRANS_GOLD_COIN_RESP_4006.getCode());
        }
        Integer goldCoinNum =  Integer.valueOf(transValue);
        Integer addOrSub = TransTypeEnums.getAddOrSub(transType);
        String transTypeDesc = TransTypeEnums.getName(transType);
        if(goldCoinNum.compareTo(0)<=0){
            throw new ApiException(BusinessCode.TRANS_GOLD_COIN_RESP_4005.getCode());
        }
        //构建交易记录
        this.buildTransRecord(userId,transType,null,goldCoinNum,addOrSub,CommConst.TRANS_STATUS_1,null,transTypeDesc);
    }

    @Override
    public void manualCashTrans(UserBean user, TransRecord transRecord) throws ApiException {
        String userId = user.getUserId();
        Integer transType = transRecord.getTransType();
        BigDecimal money = transRecord.getMoney();
        Integer addOrSub = TransTypeEnums.getAddOrSub(transType);
        String transTypeDesc = TransTypeEnums.getName(transType);
        checkBusinessService.checkManualCash(user,money);
        this.buildTransRecord(userId,transType,money,null,addOrSub,CommConst.TRANS_STATUS_1,null,transTypeDesc);
    }

    @Override
    public Page<TransRecord> queryTrans(Map<String, Object> params) throws ApiException {
        String days = String.valueOf(params.get("days"));
        if("1".equals(days)){ //筛选今日
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            params.put("startDate",startDate);
            params.put("endDate",endDate);
        }else if("2".equals(days)){
            long startDate = DateTools.getUnixTimestampTime(DateTools.getStartTime(DateTools.addDay(new Date(),-1))); //昨日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(DateTools.addDay(new Date(),-1)));//昨日最后一秒
            params.put("startDate",startDate);
            params.put("endDate",endDate);
        }else if("3".equals(days)){
            long startDate = DateTools.getUnixTimestampTime(DateTools.getStartTime(DateTools.addDay(new Date(),-7))); //近7天凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(DateTools.addDay(new Date(),-7)));//近7天最后一秒
            params.put("startDate",startDate);
            params.put("endDate",endDate);
        }
        int total = transRecordMapper.getTotal(params);
        if (total > 0) {
            List<TransRecord> list = transRecordMapper.queryByPage(params);
            return new Page<TransRecord>(list, total);
        }else{
            return new Page<TransRecord>(null, 0);
        }
    }

    @Override
    public int buildTransRecord(String userId, Integer transType, BigDecimal money,Integer goldCoin, Integer addOrSub, Integer status,String subServiceId,String remark) {
        int res = 0;
        TransRecord transRecord = new TransRecord();
        transRecord.setUserId(userId);
        transRecord.setTransType(transType);
        transRecord.setTransNo(GenerationUtil.getTransNo());
        transRecord.setAddOrSub(addOrSub);

        UserBean user = userMapper.queryByUserId(userId);
        BigDecimal beforeMoney = user.getCoreBalance();
        Integer beforeGoldCoin = user.getGoldCoin();

        BigDecimal afterMoney = BigDecimal.ZERO;
        Integer afterGoldCoin = 0;
        boolean isGoldCoinTranFlag = false;
        //创建金币交易
        if(TransTypeEnums.goldCoinTransTypeCodeList.contains(transType)){
            isGoldCoinTranFlag = true;
            transRecord.setGoldCoin(goldCoin);
            transRecord.setBeforeGoldCoin(beforeGoldCoin);

            if(AddOrSubEnums.ADD.getCode().equals(addOrSub)){
                afterGoldCoin = beforeGoldCoin + goldCoin;
            }else if(AddOrSubEnums.SUB.getCode().equals(addOrSub)){
                afterGoldCoin = beforeGoldCoin - goldCoin;
            }else{
                afterGoldCoin = goldCoin;//不作更改，还是原来的金币余额
            }
            transRecord.setAfterGoldCoin(afterGoldCoin);


        }else if(TransTypeEnums.moneyTransTypeCodeList.contains(transType)){ //非金币交易
            transRecord.setMoney(money);
            transRecord.setBeforeMoney(beforeMoney);

            if(AddOrSubEnums.ADD.getCode().equals(addOrSub)){
                afterMoney = beforeMoney.add(money);
            }else if(AddOrSubEnums.SUB.getCode().equals(addOrSub)){
                afterMoney = beforeMoney.subtract(money);
            }else{
                afterMoney = beforeMoney;//不作更改，还是原来的余额
            }
            transRecord.setAfterMoney(afterMoney);
        }

        transRecord.setStatus(status);
        transRecord.setRemark(remark);
        transRecord.setTransTime(DateTools.getUnixTimestampTime(new Date()));

        res = transRecordMapper.insert(transRecord);
        //状态为交易成功才更新user
        if(CommConst.TRANS_STATUS_1.equals(status)){

            if(isGoldCoinTranFlag){
                //更新用户金币余额
                user.setGoldCoin(afterGoldCoin);
                res = userMapper.updateByUserId(user);
            }else{
                //更新用户核心余额
                user.setCoreBalance(afterMoney);
                res = userMapper.updateByUserId(user);
            }
        }
        return res;

    }
}

