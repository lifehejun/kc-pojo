package com.kc.biz.mapper;

import com.kc.biz.bean.VipGrade;

import java.util.List;
import java.util.Map;

public interface VipGradeMapper extends BaseMapper<VipGrade> {

    List<VipGrade> queryByPage(Map<String, Object> params);
    List<VipGrade> findList(Map<String, Object> params);
}
