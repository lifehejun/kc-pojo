package com.kc.manage.controller;

import com.kc.biz.bean.AdminUserBean;
import com.kc.biz.service.IAdminUserService;
import com.kc.biz.service.IUserService;
import com.kc.biz.vo.StatisticsVo;
import com.kc.common.exception.ApiException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AdminUserController extends BaseController{


    @Autowired
    private IAdminUserService adminUserService;
    @Autowired
    private IUserService userService;


    @RequestMapping("/admin/index")
    public String adminindex(HttpServletRequest request, HttpSession session){
        return "index";
    }


    @RequestMapping("/admin/list")
    public String index(HttpServletRequest request, HttpSession session){
        return "adminUser/admin_user_list";
    }

    @RequestMapping("/admin/welcome")
    public String welocome(HttpServletRequest request, HttpSession session){
        StatisticsVo statisticsVo = userService.findStatisticsInfo();
        request.setAttribute("statisticsVo",statisticsVo);
        return "welcome";
    }

    @RequestMapping("/login")
    public String to_login(HttpServletRequest request,String userName,String userPwd){
        return "login";
    }

    @RequestMapping(value = "/login/json",method={RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> login(HttpSession session, String userName, String userPwd){

        try {
            if(StringUtils.isBlank(userName)){
                return requestError("用户名不能为空");
            }
            if(StringUtils.isBlank(userPwd)){
                return requestError("密码不能为空");
            }
            AdminUserBean adminUserBean = adminUserService.login(userName,userPwd);
            session.setAttribute("user",adminUserBean);
            return requestSuccess("登录成功");
        }catch (ApiException e){
            logger.error("登录后台异常...");
            return exceptionHandling(e);
        }

    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }

}
