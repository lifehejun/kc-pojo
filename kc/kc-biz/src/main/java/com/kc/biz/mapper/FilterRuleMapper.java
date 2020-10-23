package com.kc.biz.mapper;

import com.kc.biz.bean.FilterRule;

import java.util.List;
import java.util.Map;

public interface FilterRuleMapper extends BaseMapper<FilterRule>  {
    List<FilterRule> queryByPage(Map<String, Object> params);
    List<FilterRule> findList(Map<String, Object> params);
    int insertBatch(List<FilterRule > reddemCodeList);

}
