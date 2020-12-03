package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.biz.bean.Video;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IVideoLabelService;
import com.kc.biz.service.IVideoService;
import com.kc.biz.vo.PostVo;
import com.kc.biz.vo.TopicShowVo;
import com.kc.biz.vo.VideoLabelVo;
import com.kc.biz.vo.VideoVo;
import com.kc.common.base.BaseRest;
import com.kc.common.page.Page;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.DateStyle;
import com.kc.common.util.DateTools;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/video")
public class VideoRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(VideoRest.class);

    @Autowired
    private IVideoService videoService;
    @Autowired
    private IVideoLabelService videoLabelService;


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
    /*@UserLoginToken
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
*/

    /**
     * 查询视频标签列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/findVideoLabelList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findVideoLabelList(HttpServletRequest request) {
        try {
            List<VideoLabelVo> list =  videoLabelService.findListByRedis();
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findVideoLabelList()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 查询视频
     * @param request
     * @param page
     * @param limit
     * @param labelCode
     * @return
     */
    @RequestMapping(value = "/findVideoPageByLabelCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findVideoPageByLabelCode(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        try {
            logger.info("findVideoPageByLabelCode request :{}",params);
            Map<String,Object> pageParams  = ReqParamsUtils.getApiPageParams(params);
            pageParams.putAll(params);
            Page<VideoVo> list =  videoService.findVideoPageByLabelCode(pageParams);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findVideoPageByLabelCode()",500);
            return exceptionHandling(e);
        }
    }


    /**
     * 根据id查询视频
     * @param request
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/findVideoById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findVideoById(HttpServletRequest request, @RequestBody Map<String, String> params) {
        try {
            logger.info("findVideoById request :{}",params);
            String videoId = params.get("videoId");
            if(StringUtils.isBlank(videoId)){
                return requestError(BusinessCode.VIDEO_ID_NULL_8201.getCode());
            }
            Video video =  videoService.queryById(Long.valueOf(videoId));
            VideoVo videoVo = new VideoVo();
            BeanUtils.copyProperties(video,videoVo);
            videoVo.setLabelNameList(videoService.getVideoLabelNameList(video.getLabelCodeList()));
            videoVo.setCreateTimeStr(DateTools.timeStampUnixTimeToStr(video.getCreateTime(), DateStyle.YYYY_MM_DD.getValue()));
            return requestSuccess(videoVo);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findVideoById()",500);
            return exceptionHandling(e);
        }
    }


    /**
     * 查询详情页推荐视频5个
     * @param request
     * @param videoId
     * @return
     */
    @RequestMapping(value = "/findDetailSugVideo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findDetailSugVideo(HttpServletRequest request ,@RequestBody Map<String, String> params) {
        try {

            List<VideoVo> list = videoService.findDetailSugVideo(params);
            return requestSuccess(list);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findDetailSugVideo()",500);
            return exceptionHandling(e);
        }
    }


}
