package com.kc.biz.mapper;

import com.kc.biz.bean.Post;
import com.kc.biz.bean.PostImage;
import com.kc.biz.bean.PostVideo;
import com.kc.biz.bean.Video;
import com.kc.biz.vo.PostVo;

import java.util.List;
import java.util.Map;

public interface PostMapper extends BaseMapper<Post> {
    List<Post> queryByPage(Map<String, Object> params);

    List<PostVo> findPostByPage(Map<String, Object> params);
    int findPostTotal(Map<String, Object> params);
    List<PostVideo> findVideoByPostId(Long postId);
    List<PostImage> findImgByPostId(Long postId);
    int insertPostVideo(PostVideo postVideo);
    int insertPostImage(PostImage postImage);


}
