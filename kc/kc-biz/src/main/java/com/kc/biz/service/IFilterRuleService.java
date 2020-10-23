package com.kc.biz.service;

import com.kc.biz.bean.FilterRule;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IFilterRuleService {
    int insert(FilterRule filterRule);
    int updateById(FilterRule filterRule);
    int deleteById(Long id);
    FilterRule queryById(Long id);
    Page<FilterRule> queryByPage(Map<String, Object> params) throws ApiException;
    List<FilterRule> findList(Map<String, Object> params);
    void importRuleExcel(MultipartFile file) throws ApiException;

    void checkLoginBlackByPhone(String phone) throws ApiException; //校验手机号登录黑名单
    void checkRegBlackByIP(String ip) throws ApiException; //校验IP注册黑名单
    void checkLoginBlackByIP(String ip) throws ApiException; //校验IP注册黑名单

}
