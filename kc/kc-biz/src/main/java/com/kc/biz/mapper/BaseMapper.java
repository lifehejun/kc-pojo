package com.kc.biz.mapper;

import java.util.Map;

public interface BaseMapper<T> {

    int insert(T entity);

    int updateById(T entity);

    int deleteById(Long id);

    T queryById(Long id);


    int getTotal(Map<String, Object> params);




}
