package com.kc.manage.controller;

import com.kc.biz.bean.Topic;
import com.kc.biz.bean.Video;
import com.kc.biz.bean.VideoLabel;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IVideoLabelService;
import com.kc.biz.service.IVideoService;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.enums.VodTypeEnums;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/video")
public class VideoController extends BaseController{

    @Autowired
    private IVideoService videoService;
    @Autowired
    private IVideoLabelService videoLabelService;
    @Autowired
    private IBusConfigService busConfigService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        List<VideoLabel> list = videoLabelService.findAll();
        Map<String,List<VideoLabel>> listMap = list.stream().collect(Collectors.groupingBy(VideoLabel::getVodType));
        Map<String,List<VideoLabel>> videoLabelGroupMap = new HashMap<String,List<VideoLabel>>();
        for (String key: listMap.keySet()){
            videoLabelGroupMap.put(VodTypeEnums.getName(key),listMap.get(key));
        }

        request.setAttribute("videoLabelGroupMap",videoLabelGroupMap);
        request.setAttribute("videoType", VodTypeEnums.vodTypeEnumsMap);
        return "video/video_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Video> page = videoService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询视频信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Video video = videoService.queryById(Long.valueOf(id));
            request.setAttribute("video",video);
        }
        request.setAttribute("videoType", VodTypeEnums.vodTypeEnumsMap);
        return "video/video_edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request, Video video) {
        try {
            if(null == video.getId()){
                videoService.insert(video);
            }else{
                videoService.updateById(video);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存视频异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}
