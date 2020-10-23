package com.kc.common.util;

import javax.servlet.http.HttpSession;

/**
 * Created by timi on 2018/4/23.
 */
public class SessionUtils {

    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USER_NAME = "userName";
    public static final String SESSION_USER_TYPE = "userType";
    public static final String SESSION_USER_PHONE = "userPhone";
    public static final String SESSION_USER_EMAIL = "userEmail";
    public static final String SESSION_USER_STATUS = "status";



    
    
    /**
     * 获取后台登录用户手机号码
     * @param session
     * @return
     */
    public static String getUserPhone(HttpSession session){
        Object userPhone = session.getAttribute(SESSION_USER_PHONE);
        return String.valueOf(userPhone);
    }
    
    
    /**
     * 获取后台登录用户ID
     * @param session
     * @return
     */
    public static String getUserId(HttpSession session){
        Object userId = session.getAttribute(SESSION_USER_ID);
        return String.valueOf(userId);
    }

    /**
     * 获取后台登录用户名
     * @param session
     * @return
     */
    public static String getUserName(HttpSession session){
        Object userName = session.getAttribute(SESSION_USER_NAME);
        return String.valueOf(userName);
    }

    /**
     * 获取后台用户类型
     * @param session
     * @return
     */
    public static Integer getUserType(HttpSession session){
        Object userType = session.getAttribute(SESSION_USER_TYPE);
        return Integer.valueOf(String.valueOf(userType));
    }

    /**
     * 获取后台用户邮箱
     * @param session
     * @return
     */
    public static String getUserEmail(HttpSession session){
        Object userEmail = session.getAttribute(SESSION_USER_EMAIL);
        return String.valueOf(userEmail);
    }

    public static Integer getUserStatus(HttpSession session){
        Object userStatus = session.getAttribute(SESSION_USER_STATUS);
        return Integer.valueOf(String.valueOf(userStatus));
    }


}
