package com.kc.biz.service.impl;

import com.kc.biz.cache.RedisUtil;
import com.kc.biz.service.ICacheService;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.enums.VerifyCodeTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public String getVerifyCode(String phone, String codeType) {
        return redisUtil.getValueByKey(RedisKeyEnums.VERIFY_CODE.getCode() + codeType +":" + phone);
    }

    @Override
    public void setVerifyCode(String phone, String codeType,String verifyCode) {
        redisUtil.setKeyAndValueTimeout(RedisKeyEnums.VERIFY_CODE.getCode() + codeType + ":" + phone,verifyCode,3600);
    }

    @Override
    public void checkVerifyCode(String verifyCode,String phone) {
        String redisVerifyCode = this.getVerifyCode(phone, VerifyCodeTypeEnums.VERIFY_CODE_TYPE_REG.getCode());
        if(StringUtils.isBlank(redisVerifyCode) || !verifyCode.equals(redisVerifyCode)){
            throw new ApiException(BusinessCode.USER_RESP_2027.getCode());
        }
    }
}
