package com.kc.biz.service;

public interface ICacheService {
    String getVerifyCode(String phone,String codeType);
    void setVerifyCode(String phone,String codeType,String verifyCode);
    void checkVerifyCode(String verifyCode,String phone);

}
