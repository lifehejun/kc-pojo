package com.kc.manage.controller;

import com.kc.biz.service.IUserService;
import com.kc.biz.vo.RegIpPhoneVo;
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
@RequestMapping("/ip")
public class IPController extends BaseController{


    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, HttpSession session) {
        return "ip/regip_list";
    }



    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<RegIpPhoneVo> page = userService.findPhoneGroupByIp(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            return exceptionHandling(e);
        }
    }




}
