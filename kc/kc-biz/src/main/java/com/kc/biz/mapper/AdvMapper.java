package com.kc.biz.mapper;

import com.kc.biz.bean.Adv;

import java.util.List;
import java.util.Map;

public interface AdvMapper extends BaseMapper<Adv> {

    List<Adv> queryByPage(Map<String, Object> params);
    List<Adv> findList();

}
