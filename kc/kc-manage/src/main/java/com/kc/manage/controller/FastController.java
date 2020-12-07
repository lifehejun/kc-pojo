package com.kc.manage.controller;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.UserBean;
import com.kc.biz.service.IBusConfigService;
import com.kc.biz.service.ITransRecordService;
import com.kc.biz.service.IUserService;
import com.kc.common.enums.GradeEnums;
import com.kc.common.enums.TransTypeEnums;
import com.kc.common.enums.UserStatusEnums;
import com.kc.common.enums.UserTypeEnums;
import com.kc.common.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 快捷操作
 */
@Controller
@RequestMapping("/fast")
public class FastController extends BaseController{


    @Autowired
    private IBusConfigService busConfigService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ITransRecordService transRecordService;


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
        request.setAttribute("vipGradeEnumsMap", GradeEnums.vipGradeEnumsMap);
        return "fast/open_video_member";
    }



    @RequestMapping("/manualOpenVideoVip/json")
    @ResponseBody
    public Map<String,Object> manualOpenVideoVip(HttpServletRequest request, String phone,String gradeCode) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(StringUtils.isBlank(phone)){
                return requestError("手机号不能为空");
            }
            if(StringUtils.isBlank(gradeCode)){
                return requestError("请选择开通会员的等级");
            }
            if(GradeEnums.GRADE_0.getCode() == Integer.valueOf(gradeCode).intValue()){
                return requestError("普通会员等级不用开通,请重新选择");
            }
            UserBean user =  userService.findByPhone(phone);
            if(null == user){
                return requestError("未查询到该用户,请重新输入");
            }
            if(UserStatusEnums.USER_STATUS_0.getStatus().equals(user.getStatus())){
                return requestError("该用户以冻结,不能进行交易,请重新输入");
            }
            TransRecord transRecord = new TransRecord();
            transRecord.setTransType(TransTypeEnums.TRANS_TYPE_103.getCode());
            transRecord.setSubServiceId(gradeCode);
            transRecordService.manualTransSubmit(user,transRecord);
            return requestSuccess("操作成功");
        }catch (ApiException e){
            logger.error("手动提交交易异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }



}