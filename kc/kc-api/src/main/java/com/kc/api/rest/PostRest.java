package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.ICosTencentService;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.vo.PostVo;
import com.kc.common.base.BaseRest;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(PostRest.class);

    @Autowired
    private IPostService postService;
    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private ICosTencentService cosTencentService;

    /**
     * 发布贴子
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> publish(HttpServletRequest request,@RequestParam(value = "files") List<MultipartFile> files, @RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            postService.postPublish(files,params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,publish()",500);
            return exceptionHandling(e);
        }
    }


    /**
     * 查询帖子，推荐
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/findPostBySug", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findPost(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            logger.info("findPostBySug request :{}",params);
            Map<String,Object> pageParams = ReqParamsUtils.getApiPageParams(params);
            pageParams.putAll(params);
            Page<PostVo> list =  postService.findPostBySug(pageParams);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findPost()",500);
            return exceptionHandling(e);
        }
    }





}
