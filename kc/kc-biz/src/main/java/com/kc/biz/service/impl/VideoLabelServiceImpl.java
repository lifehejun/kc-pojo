package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.BusConfig;
import com.kc.biz.bean.Video;
import com.kc.biz.bean.VideoLabel;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.VideoLabelMapper;
import com.kc.biz.mapper.VideoMapper;
import com.kc.biz.service.IVideoLabelService;
import com.kc.biz.service.IVideoService;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public int insert(VideoLabel entity) {
        return videoLabelMapper.insert(entity);
    }

    @Override
    public int updateById(VideoLabel entity) {
        return videoLabelMapper.updateById(entity);
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
    public List<VideoLabel> findLabelByType(Map<String, Object> params) throws ApiException {
        List<VideoLabel> videoLabelList = new ArrayList<VideoLabel>();
        String vodType = String.valueOf(params.get("vodType"));
        if(StringUtils.isBlank(vodType)){
            vodType =VodTypeEnums.ORDINARY.getCode();
        }
        String vodTypeKey = RedisKeyEnums.VOD_LABEL_TYPE.getCode() + vodType;
        String videoLabelJsonStr = redisUtil.getValueByKey(vodTypeKey);
        if(StringUtils.isNotBlank(videoLabelJsonStr)){
            videoLabelList = JSON.parseArray(videoLabelJsonStr,VideoLabel.class);
        }else{
            Map<String,Object> vodTypeMap = new HashMap<String,Object>();
            vodTypeMap.put("vodType",vodType);
            videoLabelList = videoLabelMapper.findByVodType(vodTypeMap);
            if(null != videoLabelList && videoLabelList.size()>0){
                redisUtil.setKeyAndValue(RedisKeyEnums.VOD_LABEL_TYPE.getCode()+vodType,JSON.toJSONString(videoLabelList));
            }
        }
        return videoLabelList;
     }

    @Override
    public VideoLabel findByCode(String vodType, String labelCode) throws ApiException {
        List<VideoLabel> videoLabelList = new ArrayList<VideoLabel>();
        String vodTypeKey = RedisKeyEnums.VOD_LABEL_TYPE.getCode() + vodType;
        String videoLabelJsonStr = redisUtil.getValueByKey(vodTypeKey);
        if(StringUtils.isNotBlank(videoLabelJsonStr)){
            videoLabelList = JSON.parseArray(videoLabelJsonStr,VideoLabel.class);
        }
        for(VideoLabel videoLabel : videoLabelList){
            if(labelCode.equals(videoLabel.getLabelCode())){
                return videoLabel;
            }
        }
        return null;
    }
}

