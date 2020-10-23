package com.kc.biz.service;

public interface IBaseService<T> {
    int insert(T entity);

    int updateById(T entity);

    int deleteById(Long id);

    T queryById(Long id);


}
