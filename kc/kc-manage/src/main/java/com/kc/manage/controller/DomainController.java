package com.kc.manage.controller;

import com.kc.biz.bean.Domain;
import com.kc.biz.bean.Topic;
import com.kc.biz.service.IDomainService;
import com.kc.biz.service.ITopicService;
import com.kc.common.enums.DomainTypeEnums;
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
@RequestMapping("/domain")
public class DomainController extends BaseController{


    @Autowired
    private IDomainService domainService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("domainTypeEnums", DomainTypeEnums.domainTypeEnumsMap);
        return "domain/domain_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<Domain> page = domainService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询域名信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }




    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            Domain domain = domainService.queryById(Long.valueOf(id));
            request.setAttribute("domain",domain);
        }
        request.setAttribute("statusEnums", ParamStatusEnums.paramStatusEnumsMap);
        request.setAttribute("domainTypeEnums", DomainTypeEnums.domainTypeEnumsMap);
        return "domain/domain_edit";
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request,Domain domain) {
        try {
            if(null == domain.getId()){
                domainService.insert(domain);
            }else{
                domainService.updateById(domain);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存域名异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public Map<String,Object> del(HttpServletRequest request,String id) {
        try {
            if(StringUtils.isNotBlank(id)){
                domainService.deleteById(Long.valueOf(id));
                return requestSuccess("删除成功");
            }else{
                return requestError("删除失败");
            }
        }catch (ApiException e){
            logger.error("删除域名异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


}
