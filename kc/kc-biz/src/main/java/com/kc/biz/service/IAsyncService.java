package com.kc.biz.service;

import com.kc.common.exception.ApiException;

import java.util.List;

/**
 * 异步线程处理服务类
 * Created by timi on 2018/4/21.
 */
public interface IAsyncService {
    void frozenUser(Long id) throws ApiException; //冻结用户
    void unFrozenUser(Long id) throws ApiException; //解冻用户

    void addTopicPostNum(List<String> topicCodeList) throws ApiException; //同步增加主题发帖量



}
