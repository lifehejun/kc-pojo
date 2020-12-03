package com.kc.biz.service.impl;

import com.alibaba.fastjson.JSON;
import com.kc.biz.bean.*;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.ForbidWordMapper;
import com.kc.biz.mapper.PostMapper;
import com.kc.biz.mapper.TopicMapper;
import com.kc.biz.service.*;
import com.kc.biz.vo.PostVo;
import com.kc.common.enums.PostStatusEnums;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.StringUtil;
import com.kc.common.util.TimeCountUtil;
import com.kc.common.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private ICheckBusinessService checkBusinessService;
    @Autowired
    private ICosTencentService cosTencentService;


    @Override
    public Post queryById(Long id) {
        return postMapper.queryById(id);
    }

    @Override
    public int deleteById(Long id) {
        int res = postMapper.deleteById(id);
        if(res>0){
             postMapper.deletePostImage(id);
        }
        return res;
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
                List<String> topicTitleList = cacheService.getTopicTitleList(topicCodeList);
                post.setTopicTitleList(String.join(",",topicTitleList));
            });
            return new Page<Post>(list, total);
        }else{
            return new Page<Post>(null, 0);
        }
    }

    @Override
    public Page<PostVo> findPostBySug(Map<String,Object> params) throws ApiException {
        int total = postMapper.findPostTotalBySug(params);
        List<PostVo> list = postMapper.findPostBySug(params);
        if (total > 0) {
            //日期格式
            list.forEach(postVo -> {
                String publishTimeStr = postVo.getPublishTime();
                postVo.setPublishTime(TimeCountUtil.timeCount(publishTimeStr));
                postVo.setTopicTitleList(cacheService.getTopicTitleList(postVo.getTopicCodeList()));
            });
            return new Page<PostVo>(list, total);
        }else{
            return new Page<PostVo>(null, 0);
        }
    }

    @Override
    public void manualPublish(PostVo postVo) throws ApiException{
        if(null == postVo.getId()){ //新增帖子发布
            Post post = new Post();
            String topicCodeStr = postVo.getTopicCodeList();
            String postTitle = postVo.getPostTitle();
            if(StringUtils.isBlank(postTitle)){
                throw new ApiException(BusinessCode.POST_TITLE_NULL_5103.getCode());
            }
            //判断违禁词
            checkBusinessService.checkForbidWord(postTitle);

            if(StringUtils.isBlank(topicCodeStr)){
                throw new ApiException(BusinessCode.POST_TOPIC_CODE_MIN_1_5101.getCode());
            }
            //转换为list
            List<String> topicCodeList =  Arrays.asList(topicCodeStr.split(","));
            if(topicCodeList.size()>3){
                throw new ApiException(BusinessCode.POST_TOPIC_CODE_MAX_3_5102.getCode());
            }

            post.setTopicCodeList(topicCodeStr);
            String userId = postVo.getUserId();
            post.setUserId(StringUtils.isNotBlank(userId)?userId:userService.findTestUserIdRandom());
            post.setPostTitle(postTitle);
            post.setStatus(PostStatusEnums.POST_STATUS_1.getStatus());
            int res = postMapper.insert(post);
            if(res>0){
                String postImagesList = postVo.getPostImages(); //前端用逗号","号隔开
                if(StringUtils.isNotBlank(postImagesList)){
                    List<String> postImages =  Arrays.asList(postImagesList.split(","));
                    for (String imgUrl :postImages){
                        PostImage postImage = new PostImage();
                        postImage.setPostId(post.getId());
                        postImage.setImgUrl(imgUrl);
                        postMapper.insertPostImage(postImage);
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
    public void postPublish(List<MultipartFile> files, Map<String, String> params) throws ApiException {
        String moduleName = "post";
        String topicCodeStr = params.get("topicCodeList");
        String postTitle = params.get("postTitle");
        String userId = params.get("userId");
        ValidatorUtil.validateNotEmpty(topicCodeStr,BusinessCode.POST_TOPIC_CODE_MIN_1_5101);
        ValidatorUtil.validateNotEmpty(postTitle,BusinessCode.POST_TITLE_NULL_5103);
        //转换为list
        List<String> topicCodeList =  Arrays.asList(topicCodeStr.split(","));
        if(topicCodeList.size()>3){
            throw new ApiException(BusinessCode.POST_TOPIC_CODE_MAX_3_5102.getCode());
        }

        //判断违禁词
        checkBusinessService.checkForbidWord(postTitle);

        if(null == files || files.size()<= 0){
            throw new ApiException(BusinessCode.FILE_UPLOAD_8103.getCode());
        }

        List<String> postImages = new ArrayList<String>();
        //上传图片
        for (int i=0;i<files.size();i++){
            Map<String, Object> uploadResult = cosTencentService.uploadFileToCOS(files.get(i),moduleName);
            String fileUrl = (String)uploadResult.get("fileUrl");
            postImages.add(fileUrl);
        }

        //入库post
        Post post = new Post();
        post.setTopicCodeList(topicCodeStr);
        post.setUserId(userId);
        post.setPostTitle(postTitle);
        post.setStatus(PostStatusEnums.POST_STATUS_1.getStatus());
        int res = postMapper.insert(post);
        if(res>0){
            for (String imgUrl :postImages){
                PostImage postImage = new PostImage();
                postImage.setPostId(post.getId());
                postImage.setImgUrl(imgUrl);
                postMapper.insertPostImage(postImage);
            }
            //更新帖子主题发帖量
            if(StringUtils.isNotBlank(topicCodeStr)){
                asyncService.addTopicPostNum(topicCodeList);
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

    @Override
    public int insertPostImage(PostImage postImage) {
        return postMapper.insertPostImage(postImage);
    }
}

