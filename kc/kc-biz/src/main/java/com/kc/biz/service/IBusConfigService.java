package com.kc.biz.service;

import com.kc.biz.bean.BusConfig;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IBusConfigService{
    int insert(BusConfig busConfig);
    int updateById(BusConfig entity);
    int deleteById(Long id);
    BusConfig queryById(Long id);
    Page<BusConfig> queryByPage(Map<String, Object> params) throws ApiException;
    List<BusConfig> findList();
    List<BusConfig> findListByType(String busType);
    void refresh() throws ApiException;
    List<BusConfig> findListRedisByType(String busTypeKey);
    String findName(String busTypeKey,String name);
}
