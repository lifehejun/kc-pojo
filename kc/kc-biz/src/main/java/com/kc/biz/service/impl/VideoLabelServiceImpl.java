package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kc.biz.bean.Topic;
import com.kc.biz.bean.VideoLabel;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.VideoLabelMapper;
import com.kc.biz.service.IVideoLabelService;
import com.kc.biz.vo.TopicShowVo;
import com.kc.biz.vo.VideoLabelVo;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class VideoLabelServiceImpl implements IVideoLabelService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private VideoLabelMapper videoLabelMapper;

    @Override
    public int insert(VideoLabel videoLabel) {
        int res = videoLabelMapper.insert(videoLabel);
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int updateById(VideoLabel videoLabel) {
        int res = videoLabelMapper.updateById(videoLabel);
        if(res>0){
            this.refresh();
        }
        return res;
    }

    @Override
    public int deleteById(Long id) {
        return videoLabelMapper.deleteById(id);
    }

    @Override
    public List<VideoLabel> findAll() {
        return videoLabelMapper.findAll();
    }

    @Override
    public VideoLabel queryById(Long id) {
        return videoLabelMapper.queryById(id);
    }

    @Override
    public Page<VideoLabel> queryByPage(Map<String, Object> params) throws ApiException {
        int total = videoLabelMapper.getTotal(params);
        if (total > 0) {
            List<VideoLabel> list = videoLabelMapper.queryByPage(params);
            return new Page<VideoLabel>(list, total);
        }else{
            return new Page<VideoLabel>(null, 0);
        }
    }

    @Override
    public void refresh() {
        List<VideoLabel> videoLabelList = videoLabelMapper.findAll();
        Map<String, VideoLabel> videoLabelMap = videoLabelList.stream().collect(Collectors.toMap(VideoLabel::getLabelCode, Function.identity()));
        for (String videoLabel :videoLabelMap.keySet()){
            redisUtil.setKeyAndValue(RedisKeyEnums.VOD_LABEL_CODE.getCode()+videoLabel, JSON.toJSONString(videoLabelMap.get(videoLabel)));
        }
        //存videoLabelList
        if(null != videoLabelList && videoLabelList.size()>0){
            List<VideoLabelVo> videoLabelVos = new ArrayList<VideoLabelVo>();
            for (VideoLabel videoLabel:videoLabelList){
                VideoLabelVo videoLabelVo = new VideoLabelVo();
                videoLabelVo.setLabelCode(videoLabel.getLabelCode());
                videoLabelVo.setImgUrl(videoLabel.getImgUrl());
                videoLabelVo.setLabelName(videoLabel.getLabelName());
                videoLabelVos.add(videoLabelVo);
            }
            redisUtil.setKeyAndValue(RedisKeyEnums.VOD_LABEL_CODE_LIST.getCode(), JSONArray.toJSONString(videoLabelVos));
        }
    }

    @Override
    public List<VideoLabel> findLabelByType(Map<String, Object> params) throws ApiException {
        List<VideoLabel> videoLabelList = new ArrayList<VideoLabel>();
        String vodType = String.valueOf(params.get("vodType"));
        if(StringUtils.isBlank(vodType)){
            vodType =VodTypeEnums.ORDINARY.getCode();
        }
        String vodTypeKey = RedisKeyEnums.VOD_LABEL_CODE.getCode() + vodType;
        String videoLabelJsonStr = redisUtil.getValueByKey(vodTypeKey);
        if(StringUtils.isNotBlank(videoLabelJsonStr)){
            videoLabelList = JSON.parseArray(videoLabelJsonStr,VideoLabel.class);
        }else{
            Map<String,Object> vodTypeMap = new HashMap<String,Object>();
            vodTypeMap.put("vodType",vodType);
            videoLabelList = videoLabelMapper.findByVodType(vodTypeMap);
            if(null != videoLabelList && videoLabelList.size()>0){
                redisUtil.setKeyAndValue(RedisKeyEnums.VOD_LABEL_CODE.getCode()+vodType,JSON.toJSONString(videoLabelList));
            }
        }
        return videoLabelList;
     }

    @Override
    public VideoLabel findByCode(String labelCode) throws ApiException {
        VideoLabel videoLabel = new VideoLabel();
        String vodTypeKey = RedisKeyEnums.VOD_LABEL_CODE.getCode() + labelCode;
        String videoLabelJsonStr = redisUtil.getValueByKey(vodTypeKey);
        if(StringUtils.isNotBlank(videoLabelJsonStr)){
            videoLabel = JSON.parseObject(videoLabelJsonStr,VideoLabel.class);
        }
        return videoLabel;
    }

    @Override
    public List<VideoLabelVo> findListByRedis() throws ApiException {
        String videoLabelListRedis = redisUtil.getValueByKey(RedisKeyEnums.VOD_LABEL_CODE_LIST.getCode());
        if(StringUtils.isNotBlank(videoLabelListRedis)){
            List<VideoLabelVo> list = JSONArray.parseArray(videoLabelListRedis,VideoLabelVo.class);
            return list;
        }
        return null;
    }
}

