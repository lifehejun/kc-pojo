package com.kc.manage.controller;

import com.kc.biz.bean.TransRecord;
import com.kc.biz.bean.UserBean;
import com.kc.biz.service.ITransRecordService;
import com.kc.biz.service.IUserService;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.GradeEnums;
import com.kc.common.enums.TransTypeEnums;
import com.kc.common.enums.UserStatusEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import com.kc.common.util.api.ReqParamsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/trans")
public class TransRecordController extends BaseController{



    @Autowired
    private ITransRecordService transRecordService;
    @Autowired
    private IUserService userService;


    /**
     * 充值记录
     * @param request
     * @param session
     * @return
     */
    @RequestMapping("/rechargeRecord")
    public String rechargeRecord(HttpServletRequest request, HttpSession session) {
        return "trans/recharge_record";
    }


    @RequestMapping("/cashRecord")
    public String cashRecord(HttpServletRequest request, HttpSession session) {
        return "trans/cash_record";
    }

    @RequestMapping("/allRecord")
    public String allRecord(HttpServletRequest request, HttpSession session) {
        request.setAttribute("transTypeMap",TransTypeEnums.transTypeEnumsMap);
        return "trans/trans_record";
    }




    /**
     * 根据手机号获取账户信息
     * @param request
     * @return
     */
    @RequestMapping("/findByPhone/json")
    @ResponseBody
    public Map<String,Object> findByPhone(HttpServletRequest request,String phone) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(StringUtils.isBlank(phone)){
                return requestError("手机号不能为空");
            }
            UserBean user =  userService.findByPhone(phone);
            if(null == user){
                return requestError("未查询到该用户,请重新输入");
            }
            if(UserStatusEnums.USER_STATUS_0.getStatus().equals(user.getStatus())){
                return requestError("该用户以冻结,不能进行交易,请重新输入");
            }
            user.setVodGradeName(GradeEnums.getName(user.getGrade()));
            result.put("user",user);
            return requestSuccess(result);
        }catch (ApiException e){
            logger.error("根据手机号获取账户信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    /**
     * 手动提交交易
     * @param request
     * @param phone
     * @return
     */
    @RequestMapping("/submitManualTrans/json")
    @ResponseBody
    public Map<String,Object> submitManualTrans(HttpServletRequest request,String phone,TransRecord transRecord) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(StringUtils.isBlank(phone)){
                return requestError("手机号不能为空");
            }
            UserBean user =  userService.findByPhone(phone);
            if(null == user){
                return requestError("未查询到该用户,请重新输入");
            }
            if(UserStatusEnums.USER_STATUS_0.getStatus().equals(user.getStatus())){
                return requestError("该用户以冻结,不能进行交易,请重新输入");
            }
            transRecordService.manualTransSubmit(user,transRecord);
            return requestSuccess("操作成功");
        }catch (ApiException e){
            logger.error("手动提交交易异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    /**
     * 获取充值记录
     * @param request
     * @return
     */
    @RequestMapping("/allRecord/json")
    @ResponseBody
    public Map<String,Object> allRecordJson(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<TransRecord> page = transRecordService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询交易记录异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    /**
     * 获取充值记录
     * @param request
     * @return
     */
    @RequestMapping("/rechargeRecord/json")
    @ResponseBody
    public Map<String,Object> rechargeRecord(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            params.put("transType", TransTypeEnums.TRANS_TYPE_100.getCode());
            Page<TransRecord> page = transRecordService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询充值异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


    /**
     * 获取提现记录
     * @param request
     * @return
     */
    @RequestMapping("/cashRecord/json")
    @ResponseBody
    public Map<String,Object> cashRecordJson(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            params.put("transType", TransTypeEnums.TRANS_TYPE_101.getCode());
            Page<TransRecord> page = transRecordService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询提现记录异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    /**
     * 充值确认
     * @param request
     * @return
     */
    @RequestMapping("/recharge/confirm")
    @ResponseBody
    public Map<String,Object> rechargeConfirm(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            transRecordService.rechargeConfirm(params);
            return requestSuccess("充值确认操作成功");
        }catch (ApiException e){
            logger.error("充值确认异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    /**
     * 充值撤销
     * @param request
     * @return
     */
    @RequestMapping("/recharge/return")
    @ResponseBody
    public Map<String,Object> rechargeReturn(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            transRecordService.rechargeReturn(params);
            return requestSuccess("充值rechargeReturn失败操作成功");
        }catch (ApiException e){
            logger.error("充值失败操作异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

}