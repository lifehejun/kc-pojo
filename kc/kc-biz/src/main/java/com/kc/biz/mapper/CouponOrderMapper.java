package com.kc.biz.mapper;

import com.kc.biz.bean.CouponOrder;
import com.kc.biz.vo.CouponOrderVo;

import java.util.List;
import java.util.Map;

public interface CouponOrderMapper extends BaseMapper<CouponOrder> {

    List<CouponOrderVo> findMyCouponList(Map<String, Object> params);
    int findReceivedCount(Map<String, Object> params);
}
