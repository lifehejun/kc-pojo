package com.kc.biz.service;


import com.kc.biz.bean.VideoLabel;
import com.kc.biz.bean.VipGrade;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IVipGradeService {

    int insert(VipGrade vipGrade);
    int updateById(VipGrade vipGrade);
    int deleteById(Long id);
    VipGrade queryById(Long id);
    List<VipGrade> queryByGrade(Integer grade);
    Page<VipGrade> queryByPage(Map<String, Object> params) throws ApiException;
}
