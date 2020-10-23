package com.kc.biz.mapper;

import com.kc.biz.bean.AdminUserBean;

public interface AdminUserMapper extends BaseMapper<AdminUserBean>{

    AdminUserBean queryByUserName(String userName);

}
