package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.BusConfig;
import com.kc.biz.bean.ForbidWord;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.BusConfigMapper;
import com.kc.biz.mapper.ForbidWordMapper;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IForbidWordService;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class ForbidWordServiceImpl implements IForbidWordService {



    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ForbidWordMapper forbidWordMapper;


    @Override
    public int insert(ForbidWord forbidWord) {
        return forbidWordMapper.insert(forbidWord);
    }

    @Override
    public int updateById(ForbidWord forbidWord) {
        int res = forbidWordMapper.updateById(forbidWord);
        //更新成功后刷新busConfig缓存
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return forbidWordMapper.deleteById(id);
    }

    @Override
    public ForbidWord queryById(Long id) {
        return forbidWordMapper.queryById(id);
    }

    @Override
    public Page<ForbidWord> queryByPage(Map<String, Object> params) throws ApiException {
        int total = forbidWordMapper.getTotal(params);
        if (total > 0) {
            List<ForbidWord> list = forbidWordMapper.queryByPage(params);
            return new Page<ForbidWord>(list, total);
        }else{
            return new Page<ForbidWord>(null, 0);
        }
    }

    @Override
    public List<ForbidWord> findList() {
        return forbidWordMapper.findList();
    }

    @Override
    public ForbidWord findListByKeyWord(String keyword) {
        return forbidWordMapper.findListByKeyWord(keyword);
    }

    @Override
    public void refresh() throws ApiException {
        List<ForbidWord> list = forbidWordMapper.findList();
        Map<String, List<ForbidWord>> polMapList = list.stream().collect(Collectors.groupingBy(ForbidWord::getKeyWord));
        for (String keyword:polMapList.keySet()){
            for (ForbidWord forbidWord : polMapList.get(keyword)){
                redisUtil.removeValueByKey(RedisKeyEnums.FORBID_WORD.getCode()+":"+forbidWord.getKeyWord());
            }
        }
        for (String keyword:polMapList.keySet()){
            for (ForbidWord forbidWord : polMapList.get(keyword)){
                redisUtil.setKeyAndValue(RedisKeyEnums.FORBID_WORD.getCode()+":"+forbidWord.getKeyWord(),JSON.toJSONString(forbidWord));
            }
        }
    }

    @Override
    public ForbidWord findListRedisKeyWord(String keyword) {
        String redisWord = redisUtil.getValueByKey(keyword);
        if(StringUtils.isBlank(redisWord)){
            return null;
        }
        ForbidWord forbidWord = JSON.parseObject(redisWord,ForbidWord.class);
        return forbidWord;
    }



}

