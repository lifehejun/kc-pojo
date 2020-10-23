package com.kc.biz.service.impl;

import com.kc.biz.bean.Count;
import com.kc.biz.bean.SpreadDetail;
import com.kc.biz.mapper.CountMapper;
import com.kc.biz.mapper.SpreadDetailMapper;
import com.kc.biz.mapper.TransRecordMapper;
import com.kc.biz.mapper.UserMapper;
import com.kc.biz.service.ICountService;
import com.kc.biz.service.ISpreadDetailService;
import com.kc.biz.vo.SpreadDetailVo;
import com.kc.common.consts.CommConst;
import com.kc.common.util.DateTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class CountServiceImpl implements ICountService {

    @Autowired
    private CountMapper countMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TransRecordMapper transRecordMapper;

    @Async("taskExecutor")
    @Override
    public void syncRegCount(Integer countType) {
        String currDate = DateTools.getCurrentDate();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("countDate",currDate);
        params.put("countType",countType);
        List<Count> regCounts = countMapper.findByList(params);
        if(null != regCounts && regCounts.size()>0){
            Count count = regCounts.get(0);

            Map<String,Object> todayParams = new HashMap<String,Object>();
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            todayParams.put("startCreateTime",startDate);
            todayParams.put("endCreateTime",endDate);
            int todayUserReg = userMapper.getTotal(todayParams);
            count.setValue(String.valueOf(todayUserReg));
            countMapper.updateById(count);
        }else{
            Count count = new Count();
            count.setCountDate(currDate);
            count.setCountType(countType);
            count.setValue(String.valueOf(1));//默认为1
            countMapper.insert(count);
        }
    }

    @Override
    public void syncTransCount(Integer countType) {
        String currDate = DateTools.getCurrentDate();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("countDate",currDate);
        params.put("countType",countType);
        List<Count> regCounts = countMapper.findByList(params);
        if(null != regCounts && regCounts.size()>0){
            Count count = regCounts.get(0);
            Map<String,Object> transParams = new HashMap<String,Object>();
            long startDate = DateTools.getCurrDayUnixTime(); //今日凌晨时间戳
            long endDate = DateTools.getUnixTimestampTime(DateTools.getEndTime(new Date()));//今日最后一秒
            transParams.put("startDate",startDate);
            transParams.put("endDate",endDate);
            transParams.put("status", CommConst.TRANS_STATUS_1);
            transParams.put("transType",countType); //默认统计类型为交易类型
            BigDecimal transMoney = transRecordMapper.findSumByTransType(transParams);
            count.setValue(String.valueOf(transMoney));
            countMapper.updateById(count);
        }else{
            Count count = new Count();
            count.setCountDate(currDate);
            count.setCountType(countType);
            count.setValue(String.valueOf(1));//默认为1
            countMapper.insert(count);
        }
    }
}

