package com.kc.biz.service;

import com.kc.biz.vo.SpreadDetailVo;

/**
 * Created by timi on 2018/4/21.
 */
public interface ICountService {

    void syncRegCount(Integer countType); //同步注册统计信息
    void syncTransCount(Integer countType); //同步交易统计信息
}
