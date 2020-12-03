package com.kc.biz.service;

import com.kc.biz.bean.Coupon;
import com.kc.biz.vo.CouponVo;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
public interface ICouponService {
    int insert(Coupon coupon);
    int updateById(Coupon coupon);
    int deleteById(Long id);
    Coupon queryById(Long id);
    Page<Coupon> queryByPage(Map<String, Object> params) throws ApiException;
    List<Coupon> findList();
    String getSellStatusDesc(Long sellStartTime,Long sellEndTime);
    String getValidStatusDesc(Long validStartTime,Long validEndTime);
    List<CouponVo> findAvailableCouponList(Map<String, Object> params) throws ApiException;

}
