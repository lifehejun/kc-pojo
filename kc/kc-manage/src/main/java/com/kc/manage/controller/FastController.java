package com.kc.manage.controller;

import com.kc.biz.service.IBusConfigService;
import com.kc.common.enums.TransTypeEnums;
import com.kc.common.enums.UserTypeEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 快捷操作
 */
@Controller
@RequestMapping("/fast")
public class FastController extends BaseController{


    @Autowired
    private IBusConfigService busConfigService;


    @RequestMapping("/manualOperation")
    public String manualDeposit(HttpServletRequest request, HttpSession session) {
        request.setAttribute("userTypeMap", UserTypeEnums.userTypeEnumsMap);
        //request.setAttribute("transTypeMap", TransTypeEnums.transTypeEnumsMap);
        request.setAttribute("moneyTransTypeMap", TransTypeEnums.moneyTransTypeEnumsMap);
        request.setAttribute("goldCoinTransTypeMap", TransTypeEnums.goldCoinTransTypeEnumsMap);
        return "fast/manual_operation";
    }

    @RequestMapping("/openVideoMember")
    public String openVideoMember(HttpServletRequest request, HttpSession session) {
         return "fast/open_video_member";
    }




}