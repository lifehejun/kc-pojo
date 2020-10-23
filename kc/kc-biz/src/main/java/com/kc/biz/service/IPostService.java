package com.kc.biz.service;


import com.kc.biz.bean.*;
import com.kc.biz.vo.PostVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IPostService {
    Post queryById(Long id);
    int deleteById(Long id);
    int insert(Post post);
    int updateById(Post post);
    void checkSuccess(Long id);
    Page<Post> queryByPage(Map<String, Object> params) throws ApiException;
    Page<PostVo> findPostByPage(Map<String, Object> params) throws ApiException;
    void publish(PostVo postVo);
    List<PostVideo> findVideoByPostId(Long postId);
    List<PostImage> findImgByPostId(Long postId);



}
