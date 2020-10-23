package com.kc.biz.mapper;

import com.kc.biz.bean.VideoLabel;
import com.kc.biz.vo.VideoLabelVo;

import java.util.List;
import java.util.Map;

public interface VideoLabelMapper extends BaseMapper<VideoLabel> {

    List<VideoLabel> queryByPage(Map<String, Object> params);
    List<VideoLabel> findByVodType(Map<String, Object> params);
    List<VideoLabel> findAll();
}
