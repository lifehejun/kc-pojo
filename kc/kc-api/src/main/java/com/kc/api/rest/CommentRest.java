package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.service.ICommentService;
import com.kc.biz.vo.CommentVo;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(CommentRest.class);

    @Autowired
    private ICommentService commentService;

    /**
     * 写评论
     * @param request
     * type
     * bizId
     * content
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> write(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            commentService.write(params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,write()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 获取评论
     * @param request
     * @param params bizId : vodId or postId
     * @return
     */
    @RequestMapping(value = "/findCommentByBizId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findCommentByBizId(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            Page<CommentVo> list = commentService.findListPage(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findCommentByBizId()",500);
            return exceptionHandling(e);
        }
    }
}
