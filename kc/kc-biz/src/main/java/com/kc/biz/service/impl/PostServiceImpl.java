package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.*;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.PostMapper;
import com.kc.biz.mapper.TopicMapper;
import com.kc.biz.service.IAsyncService;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.IUserService;
import com.kc.biz.vo.PostVo;
import com.kc.common.enums.PostStatusEnums;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.StringUtil;
import com.kc.common.util.TimeCountUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class PostServiceImpl implements IPostService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAsyncService asyncService;


    @Override
    public Post queryById(Long id) {
        return postMapper.queryById(id);
    }

    @Override
    public int deleteById(Long id) {
        return postMapper.deleteById(id);
    }

    @Override
    public int insert(Post topic) {
        return postMapper.insert(topic);
    }

    @Override
    public int updateById(Post post) {
        return postMapper.updateById(post);
    }

    @Override
    public void checkSuccess(Long id) {
        Post post = postMapper.queryById(id);
        if(null != post){
            post.setStatus(PostStatusEnums.POST_STATUS_1.getStatus());
            postMapper.updateById(post);
        }
    }

    @Override
    public Page<Post> queryByPage(Map<String, Object> params) throws ApiException {
        int total = postMapper.getTotal(params);
        if (total > 0) {
            List<Post> list = postMapper.queryByPage(params);
            list.forEach(post -> {
                String topicCodeList = post.getTopicCodeList();
                List<String> topicTitleList = new ArrayList<String>();
                if(StringUtils.isNotBlank(topicCodeList)){
                    String[] topicCodeArr = topicCodeList.split(",");
                    for (String topicCode: topicCodeArr){
                        String redisTopicKey = RedisKeyEnums.TOPIC_CODE.getCode()+topicCode;
                        String topicJson = redisUtil.getValueByKey(redisTopicKey);
                        Topic topic = null;
                        if(StringUtils.isNotBlank(topicJson)){
                            topic = JSON.parseObject(topicJson,Topic.class);
                        }else{
                            topic = topicMapper.queryByCode(topicCode);
                        }
                        if(null != topic){
                            topicTitleList.add(topic.getTopicTitle());
                        }
                    }
                    post.setTopicTitleList(String.join(",",topicTitleList));
                }
            });
            return new Page<Post>(list, total);
        }else{
            return new Page<Post>(null, 0);
        }
    }

    @Override
    public Page<PostVo> findPostByPage(Map<String, Object> params) throws ApiException {
        int total = postMapper.findPostTotal(params);
        if (total > 0) {
            List<PostVo> list = postMapper.findPostByPage(params);
            //日期格式
            list.forEach(postVo -> {
                String publishTimeStr = postVo.getPublishTime();
                postVo.setPublishTime(TimeCountUtil.timeCount(publishTimeStr));
                //视频

                //图片
            });
            return new Page<PostVo>(list, total);
        }else{
            return new Page<PostVo>(null, 0);
        }
    }

    @Override
    public void publish(PostVo postVo) {
        if(null == postVo.getId()){ //新增帖子发布
            Post post = new Post();
            String topicCodeStr = postVo.getTopicCodeList();
            String postTitle = postVo.getPostTitle();
            if(StringUtils.isBlank(topicCodeStr)){
                throw new ApiException(BusinessCode.POST_TOPIC_CODE_MIN_1_5101.getCode());
            }
            //转换为list
            List<String> topicCodeList =  Arrays.asList(topicCodeStr.split(","));
            if(topicCodeList.size()>3){
                throw new ApiException(BusinessCode.POST_TOPIC_CODE_MAX_3_5102.getCode());
            }
            if(StringUtils.isBlank(postTitle)){
                throw new ApiException(BusinessCode.POST_TITLE_NULL_5103.getCode());
            }

            post.setTopicCodeList(topicCodeStr);
            String userId = postVo.getUserId();
            post.setUserId(StringUtils.isNotBlank(userId)?userId:userService.findTestUserIdRandom());
            post.setPostTitle(postTitle);
            post.setStatus(PostStatusEnums.POST_STATUS_1.getStatus());
            int res = postMapper.insert(post);
            if(res>0){
                List<PostImage> postImageList = postVo.getPostImageList();
                if(null != postImageList && postImageList.size()>0){
                    for (PostImage postImage :postImageList){
                        postMapper.insertPostImage(postImage);
                    }
                }
                List<PostVideo> postVideoList = postVo.getPostVideoList();
                if(null != postVideoList && postVideoList.size()>0){
                    for (PostVideo postVideo :postVideoList){
                        postMapper.insertPostVideo(postVideo);
                    }
                }

                //更新帖子主题发帖量
                if(StringUtils.isNotBlank(topicCodeStr)){
                    asyncService.addTopicPostNum(topicCodeList);
                }
            }

        }
    }

    @Override
    public List<PostVideo> findVideoByPostId(Long postId) {
        return postMapper.findVideoByPostId(postId);
    }

    @Override
    public List<PostImage> findImgByPostId(Long postId) {
        return postMapper.findImgByPostId(postId);
    }
}

