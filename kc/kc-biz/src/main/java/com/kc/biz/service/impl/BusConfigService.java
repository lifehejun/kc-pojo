package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.BusConfig;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.BusConfigMapper;
import com.kc.biz.service.IBusConfigService;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class BusConfigService implements IBusConfigService {



    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private BusConfigMapper busConfigMapper;


    @Override
    public int insert(BusConfig busConfig) {
        return busConfigMapper.insert(busConfig);
    }

    @Override
    public int updateById(BusConfig busConfig) {
        int res = busConfigMapper.updateById(busConfig);
        //更新成功后刷新busConfig缓存
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return busConfigMapper.deleteById(id);
    }

    @Override
    public BusConfig queryById(Long id) {
        return busConfigMapper.queryById(id);
    }

    @Override
    public Page<BusConfig> queryByPage(Map<String, Object> params) throws ApiException {
        int total = busConfigMapper.getTotal(params);
        if (total > 0) {
            List<BusConfig> list = busConfigMapper.queryByPage(params);
            return new Page<BusConfig>(list, total);
        }else{
            return new Page<BusConfig>(null, 0);
        }
    }

    @Override
    public List<BusConfig> findList() {
        return busConfigMapper.findList();
    }

    @Override
    public List<BusConfig> findListByType(String busType) {
        return busConfigMapper.findListByType(busType);
    }

    @Override
    public void refresh() throws ApiException {
        List<BusConfig> list = busConfigMapper.findList();
        Map<String, List<BusConfig>> polMapList = list.stream().collect(Collectors.groupingBy(BusConfig::getBusType));
        for (String busType:polMapList.keySet()){
            for (BusConfig busConfig : polMapList.get(busType)){
                redisUtil.removeValueByKey(RedisKeyEnums.KC_DICT.getCode()+busConfig.getBusType()+":"+busConfig.getName());
            }
        }
        for (String busType:polMapList.keySet()){
            for (BusConfig busConfig : polMapList.get(busType)){
                redisUtil.setKeyAndValue(RedisKeyEnums.KC_DICT.getCode()+busConfig.getBusType()+":"+busConfig.getName(),JSON.toJSONString(busConfig));
            }
        }
    }

    @Override
    public List<BusConfig> findListRedisByType(String busTypeKey) {
        String busConfigVal = redisUtil.getValueByKey(busTypeKey);
        if(StringUtils.isBlank(busConfigVal)){
            return null;
        }
        List<BusConfig> busConfigList = JSON.parseArray(busConfigVal,BusConfig.class);
        return busConfigList;
    }



    @Override
    public String findByName(String busTypeKey, String name) {
        String busConfigVal = redisUtil.getValueByKey(busTypeKey + name);
        BusConfig busConfig = JSON.parseObject(busConfigVal,BusConfig.class);
        if(null == busConfig){
            return StringUtils.EMPTY;
        }
        return busConfig.getVal();
    }

}

