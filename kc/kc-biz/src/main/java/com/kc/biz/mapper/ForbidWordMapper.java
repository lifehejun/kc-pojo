package com.kc.biz.mapper;

import com.kc.biz.bean.ForbidWord;

import java.util.List;
import java.util.Map;

public interface ForbidWordMapper extends BaseMapper<ForbidWord>  {


    List<ForbidWord> queryByPage(Map<String, Object> params);
    List<ForbidWord> findList();
    ForbidWord findListByKeyWord(String keyword);

}
