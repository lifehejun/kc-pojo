package com.kc.biz.mapper;

import com.kc.biz.bean.Comment;
import com.kc.biz.vo.CommentVo;

import java.util.List;
import java.util.Map;

public interface CommentMapper extends BaseMapper<Comment> {


    List<Comment> queryByPage(Map<String, Object> params);
    List<CommentVo> findList(Map<String, Object> params);

}
