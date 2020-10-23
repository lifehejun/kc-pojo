package com.kc.biz.service;

import com.kc.biz.bean.Domain;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IDomainService {
    int insert(Domain domain);
    int updateById(Domain domain);
    int deleteById(Long id);
    Domain queryById(Long id);
    Page<Domain> queryByPage(Map<String, Object> params) throws ApiException;
    List<Domain> findList();
    List<Domain> findListByType(String domainType);
}
