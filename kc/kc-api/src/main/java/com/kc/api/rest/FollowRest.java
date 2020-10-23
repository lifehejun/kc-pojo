package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IUserService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.vo.FollowUserVo;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/follow")
public class FollowRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(FollowRest.class);

    @Autowired
    private IUserService userService;


    /**
     * 关注
     * @param request
     * @param request followedUserId : 被关注的userId
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> followUser(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            userService.follow(params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,followUser()",500);
            return exceptionHandling(e);
        }
    }


    /**
     * 取消关注
     * @param request
     * @param request followedUserId : 被关注的userId
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/returnFollow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> returnFollow(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            userService.returnFollow(params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,returnFollow()",500);
            return exceptionHandling(e);
        }
    }



    /**
     * 查询我的关注列表
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/findMyFollowByPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findMyFollowByPage(HttpServletRequest request) {
        try {
            Map<String,Object> params = new HashMap<String,Object>();
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            Page<FollowUserVo> list = userService.findFollowUserByPage(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findMyFollowByPage()",500);
            return exceptionHandling(e);
        }
    }


}
