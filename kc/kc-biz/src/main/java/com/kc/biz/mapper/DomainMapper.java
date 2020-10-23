package com.kc.biz.mapper;

import com.kc.biz.bean.Domain;

import java.util.List;
import java.util.Map;

public interface DomainMapper extends BaseMapper<Domain>  {


    List<Domain> queryByPage(Map<String, Object> params);
    List<Domain> findList();
    List<Domain> findListByType(String domainType);

}
