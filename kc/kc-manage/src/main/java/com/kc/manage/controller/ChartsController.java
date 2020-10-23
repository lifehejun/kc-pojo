package com.kc.manage.controller;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.IChartService;
import com.kc.biz.vo.charts.ChartsRegVo;
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

/**
 * 控制台统计
 */
@Controller
@RequestMapping("/charts")
public class ChartsController extends BaseController{


    @Autowired
    private IChartService chartService;


    @RequestMapping("/getChartsReg")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            ChartsRegVo regVo = chartService.getChartsReg();
            return requestSuccess(regVo);
        }catch (ApiException e){
            logger.error("获取注册统计信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}