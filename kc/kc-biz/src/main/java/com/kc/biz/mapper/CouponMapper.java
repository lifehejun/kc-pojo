package com.kc.biz.mapper;

import com.kc.biz.bean.Coupon;
import com.kc.biz.vo.CouponVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CouponMapper extends BaseMapper<Coupon> {

    Coupon queryByCode(@Param("couponCode") String couponCode);
    List<Coupon> queryByPage(Map<String, Object> params);
    List<Coupon> findList();
    List<CouponVo> findAvailableCouponList(Map<String, Object> params);
}
