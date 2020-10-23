package com.kc.biz.service.impl;

import com.kc.biz.bean.AdminUserBean;
import com.kc.biz.mapper.AdminUserMapper;
import com.kc.biz.service.IAdminUserService;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminUserService implements IAdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    @Override
    public int insert(AdminUserBean entity) {
        return 0;
    }

    @Override
    public int updateById(AdminUserBean entity) {
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        return 0;
    }

    @Override
    public AdminUserBean queryById(Long id) {
        return null;
    }


    @Override
    public AdminUserBean login(String userName,String userPwd) throws ApiException {
        AdminUserBean adminUserBean = adminUserMapper.queryByUserName(userName);
        if(null == adminUserBean){
            throw new ApiException(BusinessCode.USER_RESP_2005.getCode());
        }
        String md5Pwd = Md5Encrypt.md5(userPwd);
        if(!md5Pwd.equals(adminUserBean.getUserPwd())){
            throw new ApiException(BusinessCode.USER_RESP_2006.getCode());
        }
        return adminUserBean;
    }
}
