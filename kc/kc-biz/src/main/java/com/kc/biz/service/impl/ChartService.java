package com.kc.biz.service.impl;

import com.kc.biz.bean.Count;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.CountMapper;
import com.kc.biz.mapper.UserMapper;
import com.kc.biz.mapper.VipGradeMapper;
import com.kc.biz.service.IChartService;
import com.kc.biz.vo.charts.ChartsRegVo;
import com.kc.common.enums.CountTypeEnum;
import com.kc.common.util.ChartsUtil;
import com.kc.common.util.DateTools;
import net.sf.jasperreports.charts.util.ChartUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class ChartService implements IChartService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VipGradeMapper vipGradeMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CountMapper countMapper;


    @Override
    public ChartsRegVo getChartsReg() {
        ChartsRegVo chartsRegVo = new ChartsRegVo();
        chartsRegVo.setDays(ChartsUtil.getMonthDaysStr());

        //查询当月用户注册数据
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("countType", CountTypeEnum.REG.getCode());
        List<Count> currMonthCountList = countMapper.findCurrMonthByType(params);
        int[] days = ChartsUtil.getMonthDays();
        List<String> daysStr = new ArrayList<String>();
        Date nowDate = new Date();
        int currMonth = DateTools.getMonth(nowDate);
        int currYear = DateTools.getYear(nowDate);
        String monthStr = currMonth < 10 ? "0"+currMonth:String.valueOf(currMonth);
        for (int day : days){
            String dayStr = day<10?"0"+day:String.valueOf(day);
            daysStr.add(currYear+"-"+monthStr+"-"+dayStr);
        }

        Map<String,String> regCountMap = currMonthCountList.stream().collect(Collectors.toMap(Count::getCountDate,Count::getValue));

        List<String> dayData = new ArrayList<String>();

        for (String day:daysStr){
            String value = regCountMap.get(day);
            if(StringUtils.isNotBlank(value)){
                dayData.add(value);
            }else{
                dayData.add(String.valueOf(0));//默认为0
            }
        }
        chartsRegVo.setData(dayData);
        return chartsRegVo;
    }
}

