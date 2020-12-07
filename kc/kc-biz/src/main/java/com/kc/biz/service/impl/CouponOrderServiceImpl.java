package com.kc.biz.service.impl;

import com.kc.biz.bean.Coupon;
import com.kc.biz.bean.CouponOrder;
import com.kc.biz.cache.RedisUtil;
import com.kc.biz.mapper.CouponMapper;
import com.kc.biz.mapper.CouponOrderMapper;
import com.kc.biz.service.IAsyncService;
import com.kc.biz.service.ICouponOrderService;
import com.kc.biz.vo.CouponOrderVo;
import com.kc.common.consts.CommConst;
import com.kc.common.enums.*;
import com.kc.common.exception.ApiException;
import com.kc.common.resp.BusinessCode;
import com.kc.common.util.DateStyle;
import com.kc.common.util.DateTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class CouponOrderServiceImpl implements ICouponOrderService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CouponOrderMapper couponOrderMapper;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private IAsyncService asyncService;

    @Override
    public int insert(CouponOrder couponOrder) {
        int res = couponOrderMapper.insert(couponOrder);
        return res;
    }
    @Override
    public int updateById(CouponOrder couponOrder) {
        int res = couponOrderMapper.updateById(couponOrder);
        return res;
    }
    @Override
    public int deleteById(Long id) {
        return couponOrderMapper.deleteById(id);
    }

    @Override
    public CouponOrder queryById(Long id) {
        return couponOrderMapper.queryById(id);
    }

    @Override
    public String getValidTimeDesc(Long validStartTime, Long validEndTime) throws ApiException {
        Long currentTime = DateTools.getLongCurrTime();
        if(currentTime < validStartTime ){
            return CommConst.COUPON_VALID_STATUS_NO_VALID;
        }else if(currentTime >= validStartTime && currentTime <= validEndTime){
            return "有效期至 ："+ DateTools.fromUnixTime(Integer.valueOf(String.valueOf(validEndTime)), DateStyle.YYYY_MM_DD_HH_MM_SS);
        }else if(currentTime > validEndTime){
            return CommConst.COUPON_VALID_STATUS_PASS_VALID;
        }else {
            return "未知状态";
        }
    }


    @Override
    public List<CouponOrderVo> findMyCouponList(Map<String, Object> params) throws ApiException {
        List<CouponOrderVo> list = couponOrderMapper.findMyCouponList(params);
        list.forEach(couponOrderVo -> {
            couponOrderVo.setBusTypeDesc(CouponBusTypeEnums.getName(couponOrderVo.getBusType()));
            couponOrderVo.setValidTimeDesc(this.getValidTimeDesc(couponOrderVo.getValidStartTime(),couponOrderVo.getValidEndTime()));
            couponOrderVo.setOrderStatusDesc(CouponOrderStatusEnums.getName(couponOrderVo.getOrderStatus()));
        });
        return list;
    }

    @Override
    public void receiveCoupon(String userId, String couponCode) throws ApiException {
        /** 增加用户领取优惠券锁，防止重复提交 **/
        if(redisUtil.lockForReceiveCoupon(userId,couponCode)){
            try{
                //校验优惠券是否存在，是否处于销售中
                this.checkCoupon(userId,couponCode);
                //插入领取优惠券订单
                CouponOrder couponOrder = new CouponOrder();
                couponOrder.setCouponCode(couponCode);
                couponOrder.setOrderStatus(CouponOrderStatusEnums.COUPON_ORDER_STATUS_0.getCode());
                couponOrder.setUserId(userId);
                couponOrder.setReceiveTime(DateTools.getLongCurrTime());
                asyncService.addCouponOrder(couponOrder);


            }catch (Exception e){
                /** 删除用户领取优惠券锁 **/
                redisUtil.removeLockReceiveCoupon(userId,couponCode);
                if (e instanceof  ApiException) {
                    throw (ApiException) e;
                } else {
                    throw new ApiException(BusinessCode.COUPON_RECEIVE_FAIL_8302.getCode());
                }
            }

        }else{
        //提示请勿重复提交
        throw new ApiException(BusinessCode.CONSUMER000001.getCode());
        }
    }

    @Override
    public void checkCoupon(String userId, String couponCode) throws ApiException {
        Coupon coupon = couponMapper.queryByCode(couponCode);
        if(null == coupon){
            throw new ApiException(BusinessCode.COUPON_NOT_FIND_8303.getCode());
        }
        if(ParamStatusEnums.STATUS_0.getStatus().equals(coupon.getStatus())){
            throw new ApiException(BusinessCode.COUPON_INVALID_8304.getCode());
        }

        //校验是否开始或者已过销售期
        Long sellStartTime = coupon.getSellStartTime();
        Long sellEndTime = coupon.getSellEndTime();
        Long currentTime = DateTools.getLongCurrTime();
        if(currentTime < sellStartTime ){
            throw new ApiException(BusinessCode.COUPON_NO_SELL_8307.getCode());
        }else if(currentTime > sellEndTime){
            throw new ApiException(BusinessCode.COUPON_PASS_SELL_8308.getCode());
        }

        //校验优惠券是否售罄
        Map<String,Object> couponCountParams = new HashMap<>();
        couponCountParams.put("couponCode",couponCode);
        int couponCount = couponOrderMapper.findReceivedCount(couponCountParams);
        int provideNum = coupon.getProvideNum().intValue();//发放数量
        if(couponCount >= provideNum){
            throw new ApiException(BusinessCode.COUPON_SOLD_OUT_8305.getCode());
        }
        //校验优惠券每个用户最多能领取多少张
        couponCountParams.put("userId",userId);
        int couponUserCount = couponOrderMapper.findReceivedCount(couponCountParams);
        int receiveNum = coupon.getReceiveNum().intValue();//最多领取个数/人
        if(couponUserCount >= receiveNum ){
            throw new ApiException(BusinessCode.COUPON_MAX_RECEIVE_NUM_8306.getCode(),new String[]{String.valueOf(receiveNum)});
        }
    }

}

