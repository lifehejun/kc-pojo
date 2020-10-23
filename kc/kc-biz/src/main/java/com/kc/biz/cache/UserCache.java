package com.kc.biz.cache;

import com.kc.biz.bean.UserBean;
import com.kc.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

public class UserCache {

    public static UserBean refreshLoginUserInfo(UserBean user){
        UserBean userBean = new UserBean();
        if(null == user){
            return null;
        }
        userBean.setUserId(user.getUserId());
        userBean.setUserName(user.getUserName());
        userBean.setUserType(user.getUserType());
        userBean.setRealName(StringUtils.isNotBlank(user.getRealName())? StringUtil.checkNameLength(user.getRealName()):"");
        userBean.setBonusRatio(user.getBonusRatio());
        userBean.setPhone(StringUtils.isNotBlank(user.getPhone())?StringUtil.replaceWithNumStar(user.getPhone()):"");
        userBean.setAddress(user.getAddress());
        userBean.setQq(StringUtils.isNotBlank(user.getQq())?StringUtil.replaceWithNumStar(user.getQq()):"");
        userBean.setWechat(StringUtils.isNotBlank(user.getWechat())?StringUtil.replaceWithNumStar(user.getWechat()):"");
        userBean.setHeadUrl(user.getHeadUrl());
        userBean.setGrade(user.getGrade());
        userBean.setAgentCode(user.getAgentCode());
        userBean.setRegIp(user.getRegIp());
        userBean.setStatus(user.getStatus());
        userBean.setCoreBalance(user.getCoreBalance());
        return userBean;
    }

}
