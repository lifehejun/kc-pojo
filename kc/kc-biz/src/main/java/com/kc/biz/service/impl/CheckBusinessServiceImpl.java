package com.kc.biz.service.impl;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.bean.ForbidWord;
import com.kc.biz.bean.UserBean;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.ForbidWordMapper;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.ICacheService;
import com.kc.biz.service.ICheckBusinessService;
import com.kc.biz.service.IUserService;
import com.kc.common.consts.BusConfigConst;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.enums.VerifyCodeTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CheckBusinessServiceImpl implements ICheckBusinessService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ForbidWordMapper forbidWordMapper;


    @Override
    public void checkManualCash(UserBean user, BigDecimal cashMoney) throws ApiException {
        if(cashMoney.compareTo(BigDecimal.ZERO)<=0){
            throw new ApiException(BusinessCode.TRANS_RESP_4002.getCode());
        }
        //人工提现不校验最少提现金额
        /*BigDecimal min = CommConst.DEFAULT_MIN_CASH_MONEY;
        String minCashMoney = busConfigService.findName(RedisKeyEnums.CASH_CONFIG.getCode(), BusConfigConst.NAME_MIN_CASH_MONEY);
        if(StringUtils.isNotBlank(minCashMoney)){
            min = new BigDecimal(minCashMoney);
        }
        if(cashMoney.compareTo(min)<0){
            throw new ApiException(BusinessCode.TRANS_RESP_4004.getCode(),BusinessCode.TRANS_RESP_4004.getMsg().replace("{}",String.valueOf(min)));
        }*/
        BigDecimal coreBalance = user.getCoreBalance();
        //账户核心余额比提现交易余额少，禁止提现,提升余额不足
        if(coreBalance.compareTo(cashMoney)< 0){
            throw new ApiException(BusinessCode.TRANS_RESP_4003.getCode());
        }
    }

    @Override
    public void checkIpRegSize(String regIp) throws ApiException {

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("regIp",regIp);
        List<UserBean> regIpUserList = userService.findByList(params);
        int res = 0;
        if(null != regIpUserList && regIpUserList.size()>0){
            res = regIpUserList.size();
        }
        //若未配置，则不限制
        String maxRegSizeFlag = busConfigService.findByName(RedisKeyEnums.WEB_CONFIG.getCode(),BusConfigConst.NAME_MAX_REG_IP_USER_SIZE);
        if(StringUtils.isNotBlank(maxRegSizeFlag)){
            int maxRegSize = Integer.valueOf(maxRegSizeFlag).intValue();
            if(res >= maxRegSize){
                throw new ApiException(BusinessCode.USER_RESP_2032.getCode());
            }
        }

    }

    @Override
    public void checkForbidWord(String postTitle) throws ApiException{
        //判断违禁词
        List<ForbidWord> wordList = forbidWordMapper.findList();
        if(null != wordList && wordList.size()>0){
            for (ForbidWord forbidWord : wordList){
                String keyword = forbidWord.getKeyWord();
                if(postTitle.contains(keyword)){
                    throw new ApiException(BusinessCode.POST_TITLE_IS_FORBID_KEY_5104.getCode(),new String[]{keyword});
                }
            }
        }
    }
}
