package com.kc.biz.service;

import com.kc.biz.bean.Adv;
import com.kc.biz.bean.Topic;
import com.kc.biz.vo.AdvShowVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IAdvService {
    int insert(Adv adv);
    int updateById(Adv adv);
    int deleteById(Long id);
    Adv queryById(Long id);
    Page<Adv> queryByPage(Map<String, Object> params) throws ApiException;
    List<Adv> findList();
    AdvShowVo findAppAdv() throws ApiException;

}
