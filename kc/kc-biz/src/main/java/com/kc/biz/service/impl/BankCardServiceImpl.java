package com.kc.biz.service.impl;

import com.kc.biz.bean.BankCard;
import com.kc.biz.bean.Count;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.BankCardMapper;
import com.kc.biz.mapper.CountMapper;
import com.kc.biz.mapper.UserMapper;
import com.kc.biz.mapper.VipGradeMapper;
import com.kc.biz.service.IBankCardService;
import com.kc.biz.service.IChartService;
import com.kc.biz.vo.charts.ChartsRegVo;
import com.kc.common.enums.CountTypeEnum;
import com.kc.common.exception.ApiException;
import com.kc.common.util.ChartsUtil;
import com.kc.common.util.DateTools;
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
public class BankCardServiceImpl implements IBankCardService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BankCardMapper bankCardMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CountMapper countMapper;


    @Override
    public int insert(BankCard bankCard) {
        return 0;
    }

    @Override
    public int updateById(BankCard bankCard) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public BankCard queryById(Long id) {
        return null;
    }

    @Override
    public BankCard queryByUserId(String userId) throws ApiException {

        return bankCardMapper.queryByUserId(userId);
    }
}

