package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.bean.Video;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IVideoService;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class VideoRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(VideoRest.class);

    @Autowired
    private IVideoService videoService;
    @Autowired
    private IBusConfigService busConfigService;


    /**
     * 根据标签查询视频数据
     * @param request
     * @param params labelCode
     * @return
     */
    @RequestMapping(value = "/findByLabel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findByLabel(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            Page<Video> list = videoService.findByLabel(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findByLabel()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 点赞
     * @param request
     * @param videoId 视频id
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> like(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            videoService.like(params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,like()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 发布视频
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> publish(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            videoService.publish(params);
            return requestSuccess("发布成功");
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,publish()",500);
            return exceptionHandling(e);
        }
    }




}
