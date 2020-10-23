package com.kc.biz.service;

import com.kc.biz.bean.AdminUserBean;
import com.kc.common.exception.ApiException;

public interface IAdminUserService extends IBaseService<AdminUserBean>{
    //后台用户登录
    AdminUserBean login(String userName, String userPwd) throws ApiException;

}
