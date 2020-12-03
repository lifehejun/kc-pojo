package com.kc.biz.service;

import com.kc.biz.bean.Video;
import com.kc.biz.vo.VideoVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IVideoService {
    int insert(Video video);
    int updateById(Video video);
    Video queryById(Long id);
    Page<Video> queryByPage(Map<String, Object> params) throws ApiException;
    Page<VideoVo> findVideoPageByLabelCode(Map<String, Object> params) throws ApiException;

    void publish(Map<String,String> params);
    void like(Map<String,String> params);
    Page<Video> findByLabel(Map<String,Object> params);//根据视频标签查询
    List<VideoVo> findDetailSugVideo(Map<String,String> params);

    List<String> getVideoLabelNameList(String labelCodeList);

}
