package com.kc.biz.service.impl;

import com.kc.biz.bean.Comment;
import com.kc.biz.bean.TransRecord;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.CommentMapper;
import com.kc.biz.service.ICommentService;
import com.kc.biz.vo.CommentVo;
import com.kc.common.consts.CommConst;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class CommentServiceImpl implements ICommentService {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CommentMapper commentMapper;


    @Override
    public void write(Map<String, String> params) {
        String type = params.get("type");
        String bizId = params.get("bizId");
        String userId = params.get("userId");
        String content = params.get("content");
        if(StringUtils.isBlank(type)){
            throw new ApiException(BusinessCode.COMMENT_TYPE_NULL_5005.getCode());
        }
        if(StringUtils.isBlank(bizId)){
            throw new ApiException(BusinessCode.COMMENT_BIZ_ID_NULL_5001.getCode());
        }
        if(StringUtils.isBlank(content)){
            throw new ApiException(BusinessCode.COMMENT_CONT_NULL_5002.getCode());
        }
        if(content.length() > CommConst.COMMENT_CONT_MAX_LEN){
            throw new ApiException(BusinessCode.COMMENT_CONT_LEN_5003.getCode());
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setType(Integer.valueOf(type));
        comment.setBizId(Long.valueOf(bizId));
        comment.setContent(content);
        commentMapper.insert(comment);
    }

    @Override
    public Page<CommentVo> findListPage(Map<String, Object> params) {

        int total = commentMapper.getTotal(params);
        if (total > 0) {
            List<CommentVo> list = commentMapper.findList(params);
            return new Page<CommentVo>(list, total);
        }else{
            return new Page<CommentVo>(null, 0);
        }
    }
}

