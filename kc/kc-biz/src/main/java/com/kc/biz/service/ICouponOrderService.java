package com.kc.biz.service;

import com.kc.biz.bean.CouponOrder;
import com.kc.biz.vo.CouponOrderVo;
import com.kc.common.exception.ApiException;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface ICouponOrderService {
    int insert(CouponOrder couponOrder);
    int updateById(CouponOrder couponOrder);
    int deleteById(Long id);
    CouponOrder queryById(Long id);

    String getValidTimeDesc(Long validStartTime,Long validEndTime) throws ApiException;
    List<CouponOrderVo> findMyCouponList(Map<String, Object> params) throws ApiException;
    void receiveCoupon(String userId,String couponCode) throws ApiException;
    void checkCoupon(String userId,String couponCode) throws ApiException;


}
