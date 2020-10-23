package com.kc.manage.controller;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import com.kc.common.enums.DictBusTypeEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cache")
public class CacheController extends BaseController{


    @Autowired
    private IBusConfigService busConfigService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        return "cache/cache_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            params.put("busType", DictBusTypeEnums.CACHE_MANAGE.getCode());
            Page<BusConfig> page = busConfigService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询基础配置信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}