package com.kc.biz.service;

import com.kc.biz.bean.UserBean;
import com.kc.biz.vo.FollowUserVo;
import com.kc.biz.vo.RegIpPhoneVo;
import com.kc.biz.vo.StatisticsVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface IUserService {
    int insert(UserBean userBean);
    int updateById(UserBean entity);
    int deleteById(Long id);
    UserBean queryById(Long id);
    UserBean queryByUserId(String userId) throws ApiException;
    UserBean findByPhone(String phone)throws ApiException;
    List<UserBean> findByList(Map<String, Object> params)throws ApiException;
    Page<RegIpPhoneVo> findPhoneGroupByIp(Map<String, Object> params) throws ApiException;
    UserBean userLogin(String phone, String userPwd, HttpServletRequest request) throws ApiException;
    Map<String,Object> userReg(Map<String, String> params, HttpServletRequest request) throws ApiException;
    Page<UserBean> queryByPage(Map<String, Object> params) throws ApiException;//查询用户列表
    void submitManualUserReg(String phone,Integer userType) throws ApiException; //人工注册用户
    StatisticsVo findStatisticsInfo(); //获取统计信息
    String findTestUserIdRandom();//随机获取一个测试用户id

    UserBean updateNickName(String userId,String newNickName) throws ApiException; //修改昵称
    int updateLoginPwd(String userId, String newPwd) throws ApiException;//修改登陆密码
    int bindPhone(String userId, String phone) throws ApiException;//绑定手机
    int bindBankCard(Map<String, String> params) throws ApiException;//绑定yh卡
    void getVerifyCode(String phone,String codeType) throws ApiException;//获取验证码
    void follow(Map<String,Object> params)throws ApiException;//关注
    void returnFollow(Map<String,Object> params)throws ApiException; //取消关注
    Page<FollowUserVo> findFollowUserByPage(Map<String, Object> params) throws ApiException;//查询我的关注列表


}
