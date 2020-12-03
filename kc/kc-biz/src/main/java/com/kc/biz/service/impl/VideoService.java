package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.*;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.VideoMapper;
import com.kc.biz.mapper.WinRankingMapper;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IVideoLabelService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.service.IWinRankingService;
import com.kc.biz.vo.PostVo;
import com.kc.biz.vo.VideoVo;
import com.kc.common.consts.CommConst;
import com.kc.common.consts.RedisConst;
import com.kc.common.enums.ParamStatusEnums;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.TimeCountUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
public class VideoService implements IVideoService {


    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private IVideoLabelService videoLabelService;



    @Override
    public int insert(Video video) {
        return videoMapper.insert(video);
    }

    @Override
    public int updateById(Video video) {
        return videoMapper.updateById(video);
    }

    @Override
    public Video queryById(Long id) {
        return videoMapper.queryById(id);
    }

    @Override
    public Page<Video> queryByPage(Map<String, Object> params) throws ApiException {
        int total = videoMapper.getTotal(params);
        if (total > 0) {
            List<Video> list = videoMapper.queryByPage(params);
            list.forEach(video -> {
                String labelCodeList = video.getLabelCodeList();
                List<String> labelNameList = new ArrayList<String>();
                if(StringUtils.isNoneBlank(labelCodeList)){
                    String[] labelCodeArr = labelCodeList.split(",");
                    for (String labelCode: labelCodeArr){
                        VideoLabel videoLabel = videoLabelService.findByCode(labelCode);
                        if(null != videoLabel){
                            labelNameList.add(videoLabel.getLabelName());
                        }
                    }
                    video.setLabelNameList(String.join(",",labelNameList));
                }
            });

            return new Page<Video>(list, total);
        }else{
            return new Page<Video>(null, 0);
        }
    }

    @Override
    public Page<VideoVo> findVideoPageByLabelCode(Map<String, Object> params) throws ApiException {

        int total = videoMapper.getVideoPageTotal(params);
        List<VideoVo> list = videoMapper.findVideoPageByLabelCode(params);
        if (total > 0) {
            list.forEach(videoVo -> {
                videoVo.setLabelNameList(this.getVideoLabelNameList(videoVo.getLabelCodeList()));
            });
            return new Page<VideoVo>(list, total);
        }else{
            return new Page<VideoVo>(null, 0);
        }
    }

    @Override
    public void publish(Map<String, String> params) {
        //TODO-HJ
        String userId = params.get("userId");
        String vodName = params.get("vodName");
        String vodType = params.get("vodType");
        String vodImgUrl = params.get("vodImgUrl");
        String vodPlayUrl = params.get("vodPlayUrl");
        Video video = new Video();
        video.setVodName(vodName);
        video.setUserId(userId);
        video.setVodType(vodType);
        video.setVodImgUrl(vodImgUrl);
        video.setVodPlayUrl(vodPlayUrl);
        video.setStatus(ParamStatusEnums.STATUS_1.getStatus()); //默认有效，无需审核

        List<VideoLabel> videoLabelList = new ArrayList<>();
        String vodTypeKey = RedisKeyEnums.VOD_LABEL_CODE.getCode() + vodType;
        String videoLabelJsonStr = redisUtil.getValueByKey(vodTypeKey);
        if(StringUtils.isNotBlank(videoLabelJsonStr)) {
            videoLabelList = JSON.parseArray(videoLabelJsonStr, VideoLabel.class);
        }
        List<String> labelCodeList = videoLabelList.stream().map(videoLabel->videoLabel.getLabelCode()).collect(Collectors.toList());
        String labelListStr = String.join(",",labelCodeList);
        video.setLabelCodeList(labelListStr);
        videoMapper.insert(video);

    }

    @Override
    public void like(Map<String, String> params) {
        String videoId = params.get("videoId");
        String userId = params.get("userId");
        if(StringUtils.isBlank(videoId)){
            throw new ApiException(BusinessCode.VIDEO_ID_NULL.getCode());
        }
        VideoLike videoLike = new VideoLike();
        videoLike.setVideoId(videoId);
        videoLike.setUserId(userId);
        videoMapper.insertVideoLike(videoLike);

        Video video = videoMapper.queryById(Long.valueOf(videoId));
        video.setLikeNum(video.getLikeNum()+1);
        videoMapper.updateById(video);
    }

    @Override
    public Page<Video> findByLabel(Map<String, Object> params) {
        String labelCode = String.valueOf(params.get("labelCode"));
        if(StringUtils.isBlank(labelCode)){
            return new Page<Video>(null, 0);
        }
        int total = videoMapper.getTotal(params);
        if (total > 0) {
            List<Video> list = videoMapper.queryByPage(params);
            return new Page<Video>(list, total);
        }else{
            return new Page<Video>(null, 0);
        }
    }

    @Override
    public List<VideoVo> findDetailSugVideo(Map<String, String> params) {
        String videoId = params.get("videoId");
        Video video = videoMapper.queryById(Long.valueOf(videoId));
        String labelCodeList = video.getLabelCodeList();
        Map<String,Object> videoParams = new HashMap<>();
        videoParams.put("labelCode",labelCodeList.split(",")[0]);
        videoParams.put("startRow",0);
        videoParams.put("pageSize",100);
        List<VideoVo> list = videoMapper.findVideoPageByLabelCode(videoParams);
        if(null != list && list.size()>0 && list.size()<5){
            return list;
        }else if(null != list && list.size()>5){
            list.forEach(videoVo ->{
                videoVo.setLabelNameList(this.getVideoLabelNameList(labelCodeList));
            });
            return list.subList(0,5);
        }
        return null;
    }

    @Override
    public List<String> getVideoLabelNameList(String labelCodeList) {
        if(StringUtils.isBlank(labelCodeList)){
            return null;
        }
        //TODO-HJ
        List<String> labelNameList = new ArrayList<String>();
        if(StringUtils.isNoneBlank(labelCodeList)){
            String[] labelCodeArr = labelCodeList.split(",");
            for (String labelCode: labelCodeArr){
                VideoLabel videoLabel = videoLabelService.findByCode(labelCode);
                if(null != videoLabel){
                    labelNameList.add(videoLabel.getLabelName());
                }
            }
        }
        return labelNameList;
    }
}

