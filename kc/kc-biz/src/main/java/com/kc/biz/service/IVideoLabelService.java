package com.kc.biz.service;


import com.kc.biz.bean.VideoLabel;
import com.kc.biz.vo.TopicShowVo;
import com.kc.biz.vo.VideoLabelVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IVideoLabelService{
    int insert(VideoLabel videoLabel);
    int updateById(VideoLabel videoLabel);
    int deleteById(Long id);
    List<VideoLabel> findAll();
    VideoLabel queryById(Long id);
    Page<VideoLabel> queryByPage(Map<String, Object> params) throws ApiException;
    void refresh();
    List<VideoLabel> findLabelByType(Map<String, Object> params) throws ApiException;
    VideoLabel findByCode(String labelCode) throws ApiException;
    List<VideoLabelVo> findListByRedis()throws ApiException;
}
