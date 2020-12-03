package com.kc.manage.controller;

import com.kc.biz.bean.Adv;
import com.kc.biz.bean.Topic;
import com.kc.biz.service.IAdvService;
import com.kc.biz.service.ITopicService;
import com.kc.common.enums.AdvPositionEnums;
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
@RequestMapping("/adv")
public class AdvController extends BaseController{


    @Autowired
    private IAdvService advService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("advPositionEnumsMap", AdvPositionEnums.advPositionEnumsMap);
        return "adv/adv_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Adv> page = advService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询广告信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }




    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Adv adv = advService.queryById(Long.valueOf(id));
            request.setAttribute("adv",adv);
        }
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("advPositionEnumsMap", AdvPositionEnums.advPositionEnumsMap);
        return "adv/adv_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,Adv adv) {
        try {
            if(null == adv.getId()){
                adv.setAdvCode(GenerationUtil.getAdvCode());
                advService.insert(adv);
            }else{
                advService.updateById(adv);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存广告异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                advService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除广告异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


}
