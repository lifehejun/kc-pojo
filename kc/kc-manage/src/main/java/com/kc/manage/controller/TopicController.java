package com.kc.manage.controller;

import com.kc.biz.bean.Topic;
import com.kc.biz.service.ITopicService;
import com.kc.common.enums.ParamStatusEnums;
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
@RequestMapping("/topic")
public class TopicController extends BaseController{


    @Autowired
    private ITopicService topicService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        return "topic/topic_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Topic> page = topicService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询话题信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }




    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Topic topic = topicService.queryById(Long.valueOf(id));
            request.setAttribute("topic",topic);
        }
        return "topic/topic_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,Topic topic) {
        try {
            if(null == topic.getId()){
                topic.setTopicCode(GenerationUtil.getTopicCode());
                topicService.insert(topic);
            }else{
                topicService.updateById(topic);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存话题异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                topicService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除话题异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/cache/refresh")
    @ResponseBody
    public Map<String,Object> refresh(HttpServletRequest request) {
        try {
            topicService.refresh();
            return requestSuccess();
        }catch (ApiException e){
            logger.error("刷话题缓存:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}
