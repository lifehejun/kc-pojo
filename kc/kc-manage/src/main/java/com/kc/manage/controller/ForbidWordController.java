package com.kc.manage.controller;

import com.kc.biz.bean.ForbidWord;
import com.kc.biz.bean.Topic;
import com.kc.biz.service.IForbidWordService;
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
@RequestMapping("/forbidWord")
public class ForbidWordController extends BaseController{


    @Autowired
    private IForbidWordService forbidWordService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        return "forbidword/word_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<ForbidWord> page = forbidWordService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询违禁词信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }




    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            ForbidWord forbidWord = forbidWordService.queryById(Long.valueOf(id));
            request.setAttribute("forbidWord",forbidWord);
        }
        return "forbidword/word_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,ForbidWord forbidWord) {
        try {
            if(null == forbidWord.getId()){
                forbidWordService.insert(forbidWord);
            }else{
                forbidWordService.updateById(forbidWord);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存违禁词异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                forbidWordService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除违禁词异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/cache/refresh")
    @ResponseBody
    public Map<String,Object> refresh(HttpServletRequest request) {
        try {
            forbidWordService.refresh();
            return requestSuccess();
        }catch (ApiException e){
            logger.error("刷新违禁词缓存:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}
