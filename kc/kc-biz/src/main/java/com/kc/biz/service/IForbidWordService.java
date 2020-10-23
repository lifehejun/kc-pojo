package com.kc.biz.service;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.bean.ForbidWord;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IForbidWordService {
    int insert(ForbidWord forbidWord);
    int updateById(ForbidWord forbidWord);
    int deleteById(Long id);
    ForbidWord queryById(Long id);
    Page<ForbidWord> queryByPage(Map<String, Object> params) throws ApiException;
    List<ForbidWord> findList();
    ForbidWord findListByKeyWord(String keyword);
    void refresh() throws ApiException;
    ForbidWord findListRedisKeyWord(String keyword);
}
