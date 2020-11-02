package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kc.biz.bean.Topic;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.TopicMapper;
import com.kc.biz.service.ITopicService;
import com.kc.biz.vo.TopicShowVo;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.GenerationUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class TopicServiceImpl implements ITopicService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TopicMapper topicMapper;


    @Override
    public int add(Map<String, Object> params) {
        Topic topic = new Topic();
        topic.setTopicCode(String.valueOf(GenerationUtil.getThreeDig()));
        topic.setTopicTitle(String.valueOf(params.get("topicTitle")));
        topic.setTopicDesc(String.valueOf(params.get("topicDesc")));
        topic.setTopicImgUrl(null == params.get("topicDesc") ? "" :String.valueOf(params.get("topicDesc")));
        int res = topicMapper.insert(topic);

        //刷新缓存
        List<Topic> topicList = topicMapper.findList();
        Map<String, Topic> topicMap = topicList.stream().collect(Collectors.toMap(Topic::getTopicCode, Function.identity()));
        for (String topicCode :topicMap.keySet()){
            redisUtil.setKeyAndValue(RedisKeyEnums.TOPIC_CODE.getCode()+topicCode, JSON.toJSONString(topicMap.get(topicCode)));
        }
        return res;
    }

    @Override
    public int insert(Topic topic) {
        int res = topicMapper.insert(topic);
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int updateById(Topic topic) {
        int res = topicMapper.updateById(topic);
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return topicMapper.deleteById(id);
    }

    @Override
    public Topic queryById(Long id) {
        return topicMapper.queryById(id);
    }

    @Override
    public Page<Topic> queryByPage(Map<String, Object> params) throws ApiException {
        int total = topicMapper.getTotal(params);
        if (total > 0) {
            List<Topic> list = topicMapper.queryByPage(params);
            return new Page<Topic>(list, total);
        }else{
            return new Page<Topic>(null, 0);
        }
    }

    @Override
    public void refresh() {
        List<Topic> topicList = topicMapper.findList();
        Map<String, Topic> topicMap = topicList.stream().collect(Collectors.toMap(Topic::getTopicCode, Function.identity()));
        for (String topicCode :topicMap.keySet()){
            redisUtil.setKeyAndValue(RedisKeyEnums.TOPIC_CODE.getCode()+topicCode, JSON.toJSONString(topicMap.get(topicCode)));
        }
        //存topic list
        if(null != topicList && topicList.size()>0){
            List<TopicShowVo> topicShowVos = new ArrayList<TopicShowVo>();
            for (Topic topic:topicList){
                TopicShowVo topicShowVo = new TopicShowVo();
                topicShowVo.setId(topic.getId());
                topicShowVo.setTopicCode(topic.getTopicCode());
                topicShowVo.setTopicDesc(topic.getTopicDesc());
                topicShowVo.setTopicTitle(topic.getTopicTitle());
                topicShowVo.setTopicImgUrl(topic.getTopicImgUrl());
                topicShowVos.add(topicShowVo);
            }
            redisUtil.setKeyAndValue(RedisKeyEnums.TOPIC_CODE_LIST.getCode(), JSONArray.toJSONString(topicShowVos));
        }

    }

    @Override
    public List<Topic> findList() {
        return topicMapper.findList();
    }

    @Override
    public List<TopicShowVo> findListByRedis() throws ApiException {
        String topicListRedis = redisUtil.getValueByKey(RedisKeyEnums.TOPIC_CODE_LIST.getCode());
        if(StringUtils.isNotBlank(topicListRedis)){
            List<TopicShowVo> list = JSONArray.parseArray(topicListRedis,TopicShowVo.class);
            return list;
        }
        return null;
    }


}

