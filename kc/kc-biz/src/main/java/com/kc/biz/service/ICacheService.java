package com.kc.biz.service;

import java.util.List;

public interface ICacheService {
    String getVerifyCode(String phone,String codeType);
    void setVerifyCode(String phone,String codeType,String verifyCode);
    void checkVerifyCode(String verifyCode,String phone);
    List<String> getTopicTitleList(String topicCodeList);

}
