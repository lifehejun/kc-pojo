package com.kc.manage.controller;

import com.kc.biz.bean.BankCard;
import com.kc.biz.bean.UserBean;
import com.kc.biz.service.IBankCardService;
import com.kc.biz.service.IAsyncService;
import com.kc.biz.service.IUserService;
import com.kc.biz.service.IVideoService;
import com.kc.common.enums.CashBankCardEnums;
import com.kc.common.enums.UserTypeEnums;
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
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{


    @Autowired
    private IUserService userService;

    @Autowired
    private IAsyncService asyncService;
    @Autowired
    private IVideoService videoService;
    @Autowired
    private IBankCardService bankCardService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        request.setAttribute("userTypeMap", UserTypeEnums.userTypeEnumsMap);
        return "user/user_list";
    }


    @RequestMapping("/list/page")
    @ResponseBody
    public Map<String,Object> pageList(HttpServletRequest request) {
        try {
            Map<String,Object> params = ReqParamsUtils.getParamsData(request);
            Page<UserBean> page = userService.queryByPage(params);
            return requestSuccess(page.getData(),page.getCount());
        }catch (ApiException e){
            logger.error("查询用户信息异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }

    @RequestMapping("/edit")
    public String edit(HttpServletRequest request, HttpSession session,String id) {
        if(StringUtils.isNotBlank(id)){
            UserBean user = userService.queryById(Long.valueOf(id));
            request.setAttribute("user",user);

            //银行卡信息
            BankCard bankCard = bankCardService.queryByUserId(user.getUserId());
            request.setAttribute("bankCard",bankCard);
            request.setAttribute("bankCardMap", CashBankCardEnums.cashBankCardEnumsMap);
        }
        return "user/user_edit";
    }

    @RequestMapping("/frozenUser")
    @ResponseBody
    public Map<String,Object> frozenUser(HttpServletRequest request,String id) {
        try {
            asyncService.frozenUser(Long.valueOf(id));
            return requestSuccess();
        }catch (ApiException e){
            logger.error("冻结用户出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


    @RequestMapping("/unFrozenUser")
    @ResponseBody
    public Map<String,Object> unFrozenUser(HttpServletRequest request,String id) {
        try {
            asyncService.unFrozenUser(Long.valueOf(id));
            return requestSuccess();
        }catch (ApiException e){
            logger.error("解冻用户出现异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


    @RequestMapping("/submitManualUserReg/json")
    @ResponseBody
    public Map<String,Object> submitManualUserReg(HttpServletRequest request,String regPhone,String userType) {
        try {
            if(StringUtils.isBlank(regPhone)){
                return requestError("用户手机号不能为空,提交失败");
            }
            if(StringUtils.isBlank(userType)){
                return requestError("用户类型不能为空,提交失败");
            }
            Integer userTypeInt = Integer.valueOf(userType);
            if(StringUtils.isBlank(UserTypeEnums.getName(userTypeInt))){
                return requestError("不存在该类型的用户,提交失败");
            }
            userService.submitManualUserReg(regPhone,userTypeInt);
            return requestSuccess();
        }catch (ApiException e){
            logger.error("人工注册用户异常:{}",e.getMessage());
            return exceptionHandling(e);
        }
    }


}