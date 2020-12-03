package com.kc.manage.controller;

import com.kc.biz.bean.VideoLabel;
import com.kc.biz.service.IVideoLabelService;
import com.kc.common.enums.VodTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.GenerationUtil;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/videoLabel")
public class VideoLabelController extends BaseController{


    @Autowired
    private IVideoLabelService videoLabelService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("videoType", VodTypeEnums.vodTypeEnumsMap);
        return "video/label_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<VideoLabel> page = videoLabelService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询视频标签信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            VideoLabel videoLabel = videoLabelService.queryById(Long.valueOf(id));
            request.setAttribute("videoLabel",videoLabel);
        }
        request.setAttribute("videoType", VodTypeEnums.vodTypeEnumsMap);
        return "video/label_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,VideoLabel videoLabel) {
        try {
            if(null == videoLabel.getId()){
                videoLabel.setLabelCode(GenerationUtil.getVideoLabelCode());
                videoLabelService.insert(videoLabel);
            }else{
                videoLabelService.updateById(videoLabel);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存视频标签异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                videoLabelService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除视频标签异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/cache/refresh")
    @ResponseBody
    public Map<String,Object> refresh(HttpServletRequest request) {
        try {
            videoLabelService.refresh();
            return requestSuccess();
        }catch (ApiException e){
            logger.error("刷新视频标签缓存:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}