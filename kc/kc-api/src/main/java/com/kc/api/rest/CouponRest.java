package com.kc.api.rest;

import com.kc.biz.service.ICouponService;
import com.kc.biz.vo.CouponVo;
import com.kc.common.base.BaseRest;
import com.kc.common.enums.CouponBusTypeEnums;
import com.kc.common.util.DateStyle;
import com.kc.common.util.DateTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coupon")
public class CouponRest extends BaseRest {

    private static final Logger logger = LoggerFactory.getLogger(CouponRest.class);
    @Autowired
    private ICouponService couponService;

    /**
     * 查询可领取的优惠券
     * @param request
     * @return
     */
    @RequestMapping(value = "/findAvailableCouponList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findAvailableCouponList(HttpServletRequest request) {
        try {
            Map<String,Object> params = new HashMap<>();
            params.put("currentTime", DateTools.getLongCurrTime());
            List<CouponVo> couponVoList =  couponService.findAvailableCouponList(params);
            Map<Integer, List<CouponVo>> couponVoMap = couponVoList.stream().collect(Collectors.groupingBy(CouponVo::getBusType));
            return requestSuccess(couponVoMap);
        } catch (Exception e) {
            logger.info("ERROR:{}系统异常,findAvailableCouponList()",500);
            return exceptionHandling(e);
        }
    }

}
