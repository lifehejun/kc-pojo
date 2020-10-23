package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.vo.PostVo;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(PostRest.class);

    @Autowired
    private IPostService postService;
    @Autowired
    private IBusConfigService busConfigService;

    /**
     * 发布贴子
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> like(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,publish()",500);
            return exceptionHandling(e);
        }
    }


    /**
     * 查询帖子，最新，最热，评论最多
     * @param request
     * @param topicCode
     * @param sortType: maxNew maxHot maxComm
     * @return
     */
    @RequestMapping(value = "/findPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findPost(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            logger.info("findPost params: {}",params.toString());
            Page<PostVo> list =  postService.findPostByPage(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findPost()",500);
            return exceptionHandling(e);
        }
    }

}
