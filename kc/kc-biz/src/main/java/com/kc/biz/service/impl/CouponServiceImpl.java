package com.kc.biz.service.impl;

import com.kc.biz.bean.Coupon;
import com.kc.biz.mapper.CouponMapper;
import com.kc.biz.service.ICouponService;
import com.kc.common.enums.CouponBusTypeEnums;
import com.kc.common.enums.CouponGiveTypeEnums;
import com.kc.common.enums.CouponTypeEnums;
import com.kc.common.enums.ParamStatusEnums;
import com.kc.common.exception.ApiException;
import com.kc.common.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by timi on 2018/4/21.
 */
@Service
@Transactional
public class CouponServiceImpl implements ICouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Override
    public int insert(Coupon coupon) {
        int res = couponMapper.insert(coupon);
        return res;
    }
    @Override
    public int updateById(Coupon coupon) {
        int res = couponMapper.updateById(coupon);
        return res;
    }
    @Override
    public int deleteById(Long id) {
        return couponMapper.deleteById(id);
    }

    @Override
    public Coupon queryById(Long id) {
        return couponMapper.queryById(id);
    }

    @Override
    public Page<Coupon> queryByPage(Map<String, Object> params) throws ApiException {
        int total = couponMapper.getTotal(params);
        if (total > 0) {
            List<Coupon> list = couponMapper.queryByPage(params);
            list.forEach(coupon -> {
                coupon.setCouponTypeDesc(CouponTypeEnums.getName(coupon.getCouponType()));
                coupon.setGiveTypeDesc(CouponGiveTypeEnums.getName(coupon.getGiveType()));
                coupon.setBusTypeDesc(CouponBusTypeEnums.getName(coupon.getBusType()));
                coupon.setStatusDesc(ParamStatusEnums.getName(coupon.getStatus()));
            });
            return new Page<Coupon>(list, total);
        }else{
            return new Page<Coupon>(null, 0);
        }
    }



    @Override
    public List<Coupon> findList() {
        return couponMapper.findList();
    }


}

