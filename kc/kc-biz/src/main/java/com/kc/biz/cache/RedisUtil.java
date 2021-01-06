package com.kc.biz.cache;

import com.kc.common.consts.RedisConst;
import com.kc.common.enums.RedisKeyEnums;
import com.kc.common.util.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil extends BaseRedis {


    /**
     * 注入template
     */
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected RedisTemplate<String, String> getTemplate() {
        return this.redisTemplate;
    }


    /**
     * 用户注册锁
     * @param phone
     * @return
     */
    public boolean lockForRegister(String phone) {
        // 过期时间5分钟
        return setKeyAndValueTimeountIfAbsent(RedisKeyEnums.LOCK_USER_REGISTER.getCode() + phone,DateTools.getSysCurrTime(), 300);
    }

    /**
     * 删除用户注册锁
     * @param phone
     */
    public void removeLockForRegister(String phone) {
        removeValueByKey(RedisKeyEnums.LOCK_USER_REGISTER.getCode() + phone);
    }

    /**
     * 用户领取优惠券锁
     * @param userId
     * @param couponCode
     * @return
     */
    public boolean lockForReceiveCoupon(String userId,String couponCode) {
        // 过期时间5分钟
        return setKeyAndValueTimeountIfAbsent(RedisKeyEnums.LOCK_USER_RECEIVE_COUPON.getCode() + userId +":"+couponCode,DateTools.getSysCurrTime(), 300);
    }

    /**
     * 删除用户领取优惠券锁
     * @param userId
     * @param couponCode
     */
    public void removeLockReceiveCoupon(String userId,String couponCode) {
        removeValueByKey(RedisKeyEnums.LOCK_USER_RECEIVE_COUPON.getCode() + userId+":"+couponCode);
    }


    /**
     * 用户用户会员下单锁
     * @param userId
     * @param subServiceId
     * @return
     */
    public boolean lockForMemberOrder(String userId,String subServiceId) {
        // 过期时间5分钟
        return setKeyAndValueTimeountIfAbsent(RedisKeyEnums.LOCK_USER_MEMBER_ORDER.getCode() + userId +":"+subServiceId,DateTools.getSysCurrTime(), 300);
    }

    /**
     * 删除用户会员下单锁
     * @param userId
     * @param subServiceId
     */
    public void removeLockMemberOrder(String userId,String subServiceId) {
        removeValueByKey(RedisKeyEnums.LOCK_USER_MEMBER_ORDER.getCode() + userId+":"+subServiceId);
    }


}
