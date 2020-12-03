package com.kc.biz.mapper;

import com.kc.biz.bean.Video;
import com.kc.biz.bean.VideoLike;
import com.kc.biz.vo.VideoVo;

import java.util.List;
import java.util.Map;

public interface VideoMapper extends BaseMapper<Video> {

    List<Video> queryByPage(Map<String, Object> params);
    List<VideoVo> findVideoPageByLabelCode(Map<String, Object> params);
    int getVideoPageTotal(Map<String, Object> params);
    int insertVideoLike(VideoLike videoLike);

}
