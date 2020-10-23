package com.kc.biz.mapper;

import com.kc.biz.bean.Topic;
import com.kc.biz.bean.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TopicMapper extends BaseMapper<Topic> {

    Topic queryByCode(@Param("topicCode") String topicCode);
    List<Topic> queryByPage(Map<String, Object> params);
    List<Topic> findList();

}
