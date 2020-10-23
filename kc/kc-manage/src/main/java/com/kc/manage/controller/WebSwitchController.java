package com.kc.manage.controller;

import com.kc.biz.bean.BusConfig;
import com.kc.biz.service.IBusConfigService;
import com.kc.common.enums.DictBusTypeEnums;
import com.kc.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/webSwitch")
public class WebSwitchController extends BaseController{


    @Autowired
    private IBusConfigService busConfigService;


    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        List<BusConfig> webSwitchList = busConfigService.findListByType(DictBusTypeEnums.WEB_SWITCH.getCode());
        request.setAttribute("webSwitchList",webSwitchList);
        return "config/web_switch";
    }


    /**
     * 修改网站配置信息
     * @param request
     * @param busConfig
     * @return
     */
    @RequestMapping("/update/json")
    @ResponseBody
    public Map<String,Object> update(HttpServletRequest request,BusConfig busConfig) {
        try {
            if(null  == busConfig.getId()){
                return requestError("id参数不能为空");
            }
            busConfigService.updateById(busConfig);
            return requestSuccess("修改成功");
        }catch (ApiException e){
            logger.error("修改网站配置信息失败:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


}