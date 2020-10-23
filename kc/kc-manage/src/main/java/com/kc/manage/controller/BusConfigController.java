package com.kc.manage.controller;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.bean.Domain;
import com.kc.biz.service.IBusConfigService;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/busconfig")
public class BusConfigController extends BaseController{


    @Autowired
    private IBusConfigService busConfigService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        return "busconfig/busconfig_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<BusConfig> page = busConfigService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询基础配置信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/cache/refresh")
    @ResponseBody
    public Map<String,Object> refresh(HttpServletRequest request) {
        try {
            busConfigService.refresh();
            return requestSuccess();
        }catch (ApiException e){
            logger.error("刷新字典缓存:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/save")
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest request, BusConfig busConfig) {
        try {
            if(null == busConfig.getId()){
                busConfigService.insert(busConfig);
            }else{
                busConfigService.updateById(busConfig);
            }
            return requestSuccess("保存成功");
        }catch (ApiException e){
            logger.error("保存busconfig异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


}