package com.kc.api.rest;

import com.kc.api.annotation.UserLoginToken;
import com.kc.api.utils.ApiCommConst;
import com.kc.biz.bean.UserBean;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.cache.UserCache;
import com.kc.biz.service.ITokenService;
import com.kc.biz.service.IUserService;
import com.kc.common.base.BaseRest;
import com.kc.common.consts.BankConst;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.StringUtil;
import com.kc.common.util.api.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AX on 2018/6/16.
 */
@RestController
@RequestMapping("/api/user")
public class UserRest  extends BaseRest{

    private static final Logger logger = LoggerFactory.getLogger(UserRest.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private RedisUtil redisUtil;



    /**
     * 登录
     * @param request
     * @param params phone
     * @param params userPwd
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Map<String,Object> result= new HashMap<String,Object>();
        try {
            logger.info("login params : {}",params.toString());
            String phone = params.get("phone");
            if (StringUtil.isBlank(phone)) {
                return requestError(BusinessCode.USER_RESP_2009.getCode());
            }
            String userPwd = params.get("userPwd");
            if (StringUtil.isBlank(userPwd)) {
                return requestError(BusinessCode.USER_RESP_2003.getCode());
            }
            try {
                //解密登陆密码
                userPwd = AesUtil.decrypt(userPwd, ApiCommConst.API_AES_KEY,ApiCommConst.APP_AES_IV);
                logger.info("after userPwd:{}",userPwd);
            }catch (Exception e){
                return requestError(BusinessCode.USER_RESP_2004.getCode());
            }

            UserBean userBean = userService.userLogin(phone, userPwd,request);
            result.put("user",UserCache.refreshLoginUserInfo(userBean));
            result.put("token",tokenService.getToken(userBean));
            return requestSuccess(result);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,login",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 获取验证码
     * @param request
     * @param params phone
     * @param params codeType
     * @return
     */
    @RequestMapping(value = "/getVerifyCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getVerifyCode(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Map<String,Object> result= new HashMap<String,Object>();
        try {
            logger.info("getVerifyCode params : {}",params.toString());
            String phone = params.get("phone"); //用手机号作为用户名
            String codeType = params.get("codeType"); //验证码类型
            userService.getVerifyCode(phone, codeType);
            return requestSuccess("获取验证码成功");
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,getRegVerifyCode",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 注册
     * @param request
     * @param phone not null
     * @param userPwd not null
     * @param verifyCode not null
     * @param agentCode null
     * @return
     */
    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> reg(HttpServletRequest request, @RequestBody Map<String, String> params) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            logger.info("reg params : {}",params.toString());
            String userPwd = params.get("userPwd");
            if (StringUtil.isBlank(userPwd)) {
                return requestError(BusinessCode.USER_RESP_2003.getCode());
            }
            logger.info("before userPwd :{}"+userPwd);
            try {
                //解密登陆密码
                userPwd = AesUtil.decrypt(userPwd,ApiCommConst.API_AES_KEY,ApiCommConst.APP_AES_IV);
                logger.info("after userPwd:{}",userPwd);
            }catch (Exception e){
                return requestError(BusinessCode.USER_RESP_2004.getCode());
            }
            params.put("userPwd",userPwd); //解密后的明文用户密码
            result  = userService.userReg(params,request);
            return requestSuccess(result);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,reg()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 获取用户主账户余额
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/queryCoreBalance", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCoreBalance(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String userId = (String)request.getAttribute("userId");
            UserBean  userBean = userService.queryByUserId(userId);
            result.put("coreBalance",userBean.getCoreBalance().setScale(4, BigDecimal.ROUND_HALF_UP));
            return requestSuccess(result);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,queryCoreBalance()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryUserInfo(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String userId = (String)request.getAttribute("userId");
            UserBean  userBean = userService.queryByUserId(userId);
            result.put("user",UserCache.refreshLoginUserInfo(userBean));
            return requestSuccess(result);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,queryUserInfo()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 修改登录密码
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/updateLoginPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateLoginPwd(HttpServletRequest request,@RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            String newPwd = params.get("newPwd");
            if(StringUtil.isBlank(newPwd)){
                return requestError("登录密码不能为空");
            }
            try {
                //解密登陆密码
                newPwd = AesUtil.decrypt(newPwd,ApiCommConst.API_AES_KEY,ApiCommConst.APP_AES_IV);
                logger.info("after newPwd:{}",newPwd);
            }catch (Exception e){
                return requestError(BusinessCode.USER_RESP_2004.getCode());
            }
            userService.updateLoginPwd(userId,newPwd);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,updateLoginPwd()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 绑定手机号
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/bindPhone", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bindPhone(HttpServletRequest request,@RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            String phone = params.get("phone");
            userService.bindPhone(userId,phone);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,bindPhone()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 绑定银行卡
     * @param request
     * @return
     */
    @UserLoginToken
    @RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bindBankCard(HttpServletRequest request,@RequestBody Map<String, String> params) {
        try {
            String userId = (String)request.getAttribute("userId");
            params.put("userId",userId);
            userService.bindBankCard(params);
            return requestSuccess();
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,bindBankCard()",500);
            return exceptionHandling(e);
        }
    }

    /**
     * 加载可选银行列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryChooseBankList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryChooseBankList(HttpServletRequest request) {
        try {
            Map<String,String> res = BankConst.OUT_MONEY_BANK_CODE_INFO;
            return requestSuccess(res);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,queryChooseBankList()",500);
            return exceptionHandling(e);
        }
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getToken(HttpServletRequest request,String phone) {
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            String token = redisUtil.getValueByKey(RedisKeyEnums.AUTH_USER_TOKEN.getCode()+phone);
            result.put("token",token);
            return requestSuccess(result);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,getToken()",500);
            return exceptionHandling(e);
        }
    }
}
