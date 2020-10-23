package com.kc.biz.mapper;

import com.kc.biz.bean.Follow;
import com.kc.biz.bean.UserBean;
import com.kc.biz.bean.VideoLike;
import com.kc.biz.vo.FollowUserVo;
import com.kc.biz.vo.RegIpPhoneVo;

import java.util.List;
import java.util.Map;


/**
 * Created by mark on 2018/4/21.
 */
public interface UserMapper extends BaseMapper<UserBean>{
    int updateByUserId(UserBean userBean);
    UserBean findByPhone(String phone);
    UserBean queryByUserId(String userId);
    UserBean queryByAgentCode(String agentCode);
    UserBean queryByUserName(String userName);
    UserBean queryByPhone(String phone);
    List<RegIpPhoneVo> findPhoneGroupByIp(Map<String, Object> params);
    int findPhoneGroupByIpTotal(Map<String, Object> params);
    List<UserBean> queryByPage(Map<String, Object> params);
    List<UserBean> findByList(Map<String, Object> params);
    int insertFollow(Follow follow);
    int isFollowed(Follow follow);
    int deleteFollowById(Follow follow);
    List<FollowUserVo> findFollowUserByPage(Map<String, Object> params);
    int getFollowUserTotal(Map<String, Object> params);
}
