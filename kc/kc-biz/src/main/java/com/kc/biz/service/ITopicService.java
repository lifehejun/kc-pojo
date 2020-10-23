package com.kc.biz.service;

import com.kc.biz.bean.Topic;
import com.kc.biz.bean.VideoLabel;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface ITopicService {
    int add(Map<String, Object> params);
    int insert(Topic topic);
    int updateById(Topic entity);
    int deleteById(Long id);
    Topic queryById(Long id);
    Page<Topic> queryByPage(Map<String, Object> params) throws ApiException;
    void refresh();
    List<Topic> findList();

}
