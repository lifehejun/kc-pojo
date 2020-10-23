package com.kc.biz.service;


import com.kc.biz.bean.TransRecord;
import com.kc.biz.vo.CommentVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface ICommentService {


    void write(Map<String, String> params);
    Page<CommentVo> findListPage(Map<String, Object> params) throws ApiException;


}
