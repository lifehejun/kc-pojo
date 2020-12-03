package com.kc.biz.service.impl;

import com.kc.biz.bean.CouponOrder;
import com.kc.biz.bean.Topic;
import com.kc.biz.bean.UserBean;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.*;
import com.kc.biz.service.*;
import com.kc.common.enums.UserStatusEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class AsyncServiceImpl implements IAsyncService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private TransRecordMapper transRecordMapper;
    @Autowired
    private BankCardMapper bankCardMapper;
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private ISpreadDetailService spreadDetailService;
    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private ICountService countService;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private CouponOrderMapper couponOrderMapper;


    @Async("taskExecutor")
    @Override
    public void frozenUser(Long id) throws ApiException {
        UserBean userBean = userMapper.queryById(id);
        if(null == userBean){
            throw new ApiException(BusinessCode.USER_RESP_2005.getCode());
        }
        if(UserStatusEnums.USER_STATUS_0.getStatus().equals(userBean.getStatus())){
            throw new ApiException(BusinessCode.USER_RESP_2007.getCode());
        }
        userBean.setStatus(UserStatusEnums.USER_STATUS_0.getStatus());
        userMapper.updateById(userBean);
    }

    @Async("taskExecutor")
    @Override
    public void unFrozenUser(Long id) throws ApiException {
        UserBean userBean = userMapper.queryById(id);
        if(null == userBean){
            throw new ApiException(BusinessCode.USER_RESP_2005.getCode());
        }
        if(UserStatusEnums.USER_STATUS_1.getStatus().equals(userBean.getStatus())){
            throw new ApiException(BusinessCode.USER_RESP_2028.getCode());
        }
        userBean.setStatus(UserStatusEnums.USER_STATUS_1.getStatus());
        userMapper.updateById(userBean);
    }

    @Async("taskExecutor")
    @Override
    public void addTopicPostNum(List<String> topicCodeList) throws ApiException {
        //更新帖子主题发帖量
        for (String topicCode : topicCodeList){
            Topic topic = topicMapper.queryByCode(topicCode);
            if(null != topic){
                topic.setPostNum(topic.getPostNum()+1);
                topicService.updateById(topic);
            }

        }
    }

    @Override
    public void addCouponOrder(CouponOrder couponOrder) throws ApiException {
        couponOrderMapper.insert(couponOrder);
    }


}

