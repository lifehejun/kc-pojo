package com.kc.biz.mapper;

import com.kc.biz.bean.BusConfig;

import java.util.List;
import java.util.Map;

public interface BusConfigMapper extends BaseMapper<BusConfig>  {


    List<BusConfig> queryByPage(Map<String, Object> params);
    List<BusConfig> findList();
    List<BusConfig> findListByType(String busType);

}
