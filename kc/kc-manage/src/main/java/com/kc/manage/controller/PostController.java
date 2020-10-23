package com.kc.manage.controller;

import com.kc.biz.bean.*;
import com.kc.biz.service.IPostService;
import com.kc.biz.service.ITopicService;
import com.kc.biz.service.IUserService;
import com.kc.biz.vo.PostVo;
import com.kc.common.enums.PostStatusEnums;
import com.kc.common.enums.UserTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController{


    @Autowired
    private IUserService userService;
    @Autowired
    private IPostService postService;
    @Autowired
    private ITopicService topicService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        return "post/post_list";
    }

    @RequestMapping("/openPublishPost")
    public String openPublishPost(HttpServletRequest request, HttpSession session) {
        Map<String,Object> prams = new HashMap<>();
        prams.put("userType", UserTypeEnums.USER_TYPE_TEST_2.getCode()); //手工发帖，只选内部测试用户
        List<UserBean> testUserList = userService.findByList(prams);
        //帖子主题标签
        List<Topic> topicList = topicService.findList();
        request.setAttribute("topicList",topicList);
        //测试用户
        request.setAttribute("testUserList",testUserList);

        return "post/post_publish";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Post> page = postService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Post post = postService.queryById(Long.valueOf(id));
            request.setAttribute("post",post);
            //帖子视频
            List<PostVideo> postVideoList = postService.findVideoByPostId(Long.valueOf(id));
            //帖子图片
            List<PostImage> postImageList = postService.findImgByPostId(Long.valueOf(id));
            request.setAttribute("postVideoList",postVideoList);
            request.setAttribute("postImageList",postImageList);
        }
        request.setAttribute("statusEnums", PostStatusEnums.postStatusEnumsMap);
        return "post/post_edit";
    }



    @RequestMapping("/checkSuccess")
    @ResponseBody
    public Map<String,Object> checkSuccess(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                postService.checkSuccess(Long.valueOf(id));
                return requestSuccess("审核通过");
            }else{
                return requestError("审核失败");
            }
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/publish")
    @ResponseBody
    public Map<String,Object> publish(HttpServletRequest request, PostVo postVo) {
        try {
            postService.publish(postVo);
            return requestSuccess("发布成功");
        }catch (ApiException e){
            logger.error("保存帖子异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                postService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }


}
